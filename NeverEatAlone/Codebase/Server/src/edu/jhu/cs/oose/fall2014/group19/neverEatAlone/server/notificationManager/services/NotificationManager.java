package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.notificationManager.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.configuration.contracts.IConfigurationHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.notificationManager.contracts.INotificationManager;

@Stateless
public class NotificationManager implements INotificationManager {

	@Inject IConfigurationHelper iConfigurationHelper;

	@Override
	public void PushNotification(List<Map<String, String>> notificatonList,
			List<String> recipientList) {

		Gson gson = new Gson();

		// each notification is to be sent out to all recipients.

		for(String recipient : recipientList){

			// index the queues by username.
			String queueName = recipient; 
			System.out.println("queName "+queueName);

			try{

				System.out.println("reaching PushNotification");
				System.out.flush();
				ConnectionFactory factory = new ConnectionFactory();
				factory.setHost(iConfigurationHelper.GetConfigurationInstance().GetIPAddress());
				Connection connection = factory.newConnection();
				Channel channel = connection.createChannel();





				channel.queueDeclare(queueName, false, false, false, null);
				String message = gson.toJson(notificatonList); 
				channel.basicPublish("", queueName, null, message.getBytes());
				System.out.println(" [x] Sent '" + message + "'");

				channel.close();
				connection.close();
			}catch(IOException e){
				System.out.println("An IO Exception occured in PushNotification." + e.getMessage());
			}

		}

	}



}
