package net.minecraft.server;

// AAM's modification - New thread OwO

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.PluginManager;

class ThreadAAMsAuth extends Thread {

    final LoginListener a;

    ThreadAAMsAuth(LoginListener loginlistener, String s) {
        super(s);
        this.a = loginlistener;
    }
    
    private void kick(int block) { // block => 0: none,  1: normal,  2: till restart
        switch (block) {
        case 1:
            LoginListener.b(this.a).loginBlockingManager.block(this.a.networkManager.getSocketAddress());
            break;
        case 2:
            LoginListener.b(this.a).loginBlockingManager.blockForever(this.a.networkManager.getSocketAddress());
            break;
        }
        this.a.disconnect("Authentication failed!");
    }
    
    private void issueCommand(String cmd) {
        MinecraftServer server = LoginListener.b(this.a);
        
        ServerCommandEvent event = new ServerCommandEvent(server.console, cmd);
        server.server.getPluginManager().callEvent(event);
        ServerCommand servercommand = new ServerCommand(event.getCommand(), server);
        
        server.server.dispatchServerCommand(server.console, servercommand);
    }

    public void run() {
        int login_port = this.a.networkManager.getRealServerPort();
        String player_name = LoginListener.d(this.a).getName();
        
        try {
            boolean guest = true, online = true, legacy = false;
            int auth_port = 0;
            String dns = null;
            
            Connection conn = LoginListener.b(this.a).createAuthDBConnetion();
            PreparedStatement authQuery = conn.prepareStatement("SELECT online, legacy, port, dns FROM player_auth WHERE name = ?");
            authQuery.setString(1, player_name);
            ResultSet result = authQuery.executeQuery();
            if (result.first()) { // found player info
                guest = false;
                online = result.getBoolean(1);
                legacy = result.getBoolean(2);
                auth_port = result.getInt(3);
                dns = result.getString(4);
            }
            
            if (guest) {
                if (login_port == 25565) { // offical port OwO
                    // remove player permission - make guest
                    this.issueCommand("rmplayer " + player_name);
                    // logged in
                    LoginListener.e().info("player " + player_name + " - guest login");
                    LoginListener.a(this.a, EnumProtocolState.READY_TO_ACCEPT);
                } else {
                    this.kick(2);
                }
            } else if (online) {
                this.a.loginStartCallback();
            } else {
                if (login_port == 25565) { // offical port OwO
                    this.kick(0);
                } else if (login_port == auth_port || (legacy && login_port == 25575)) { // correct
                    // add player permission - make player
                    this.issueCommand("addplayer " + player_name);
                    // logged in
                    LoginListener.e().info("player " + player_name + " port auth succeed!");
                    LoginListener.a(this.a, EnumProtocolState.READY_TO_ACCEPT);
                } else {
                    this.kick(1);
                }
            }
        } catch (SQLException ex) {    
            this.a.disconnect("Database error!");
            LoginListener.e().error("Couldn\'t do auth because database is unavailable");
        } catch (Exception ex) {
            this.a.disconnect("Failed to do authentication!");
            LoginListener.b(this.a).server.getLogger().log(java.util.logging.Level.WARNING, "Exception verifying " + player_name, ex);
        }
    }
}
