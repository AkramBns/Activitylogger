package tn.tests.logger.services;

import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.h2.tools.Server;


/**
 * Servlet implementation class StartupServlet
 * 
 * @author akrambns
 *
 */
public class H2StartupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(H2StartupServlet.class);
	
	private Server server;
    /**
     * Default constructor. 
     */
    public H2StartupServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// start the TCP Server
		try {
			server = Server.createTcpServer( "-tcpPort", "9123", "-tcpAllowOthers","-baseDir","/home/akrambns/projects/ActivityLog/data");
			if(!server.isRunning(true)) {
				server.start();
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		if(server != null && server.isRunning(true)) {
			server.shutdown();	
		}
		
	}

}
