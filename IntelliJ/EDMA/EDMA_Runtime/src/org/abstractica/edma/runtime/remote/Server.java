package org.abstractica.edma.runtime.remote;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server
{
	private int port;
	private IServerInstance inst;
	private ExecutorService executor;
	
	

	public Server(int port, IServerInstance inst)
	{
		super();
		this.port = port;
		this.inst = inst;
		this.executor = Executors.newFixedThreadPool(64);
	}

	public void start() throws IOException
	{
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("Server started on " + InetAddress.getLocalHost().getHostAddress() + ":" + port);
		while(true)
		{
			Socket socket = serverSocket.accept();
			ServerTask task = new ServerTask(inst, socket);
			executor.execute(task);
		}
	}
	
}
