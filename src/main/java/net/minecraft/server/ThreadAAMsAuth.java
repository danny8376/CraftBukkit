package net.minecraft.server;

// AAM's modification - New thread OwO

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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

    public void run() {
        int login_port = this.a.networkManager.getRealServerPort();
        try {
            boolean guest = true, online = true;
            int auth_port = 0;
            
            Connection conn = LoginListener.b(this.a).createAuthDBConnetion();
            PreparedStatement authQuery = conn.prepareStatement("SELECT online, port FROM player_auth WHERE name = ?");
            authQuery.setString(1, LoginListener.d(this.a).getName());
            ResultSet result = authQuery.executeQuery();
            if (result.first()) { // found player info
                guest = false;
                online = result.getBoolean(1);
                auth_port = result.getInt(2);
            }
            
            if (guest) {
                if (login_port == 25565) { // offical port OwO
                    LoginListener.e().info("player " + LoginListener.d(this.a).getName() + " - guest login");
                    LoginListener.a(this.a, EnumProtocolState.READY_TO_ACCEPT);
                } else {
                    this.kick(2);
                }
            } else if (online) {
                this.a.loginStartCallback();
            } else {
                if (login_port == 25565) { // offical port OwO
                    this.kick(0);
                } else if (login_port == auth_port) { // correct
                    LoginListener.e().info("player " + LoginListener.d(this.a).getName() + " port auth succeed!");
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
            LoginListener.b(this.a).server.getLogger().log(java.util.logging.Level.WARNING, "Exception verifying " + LoginListener.d(this.a).getName(), ex);
        }
    }
}
