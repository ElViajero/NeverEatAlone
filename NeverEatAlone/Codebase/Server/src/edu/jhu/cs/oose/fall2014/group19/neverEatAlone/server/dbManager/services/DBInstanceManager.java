package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.services;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IDBInstanceManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IDBSchemaManager;
/**
 * 
 * @author tejasvamsingh
 *
 */
@Singleton
public class DBInstanceManager implements IDBInstanceManager {

	@Inject IDBSchemaManager iDBSchemaManagerInstance;
	private GraphDatabaseService graphDatabaseInstance;

	@Override
	public GraphDatabaseService getGraphDatabaseInstance() {
		System.out.println("Inside getGraphDatabaseInstance in DBInstanceManager");

		if(graphDatabaseInstance==null){
			//initialize the DB instance in embedded mode.
			graphDatabaseInstance = new GraphDatabaseFactory().newEmbeddedDatabase( "./DBData" );
			// Register the instance for clean shutdown.
			RegisterShutdownHook();
			// define schema constraints if not already defined.
			iDBSchemaManagerInstance.setDBSchema(graphDatabaseInstance);
		}
		return graphDatabaseInstance;
	}

	/**
	 * This method ensures that the database shuts down gracefully
	 * when the JVM exits.
	 * @param graphDb
	 */
	private void RegisterShutdownHook()
	{
		// Registers a shutdown hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
		// running application).
		Runtime.getRuntime().addShutdownHook( new Thread()
		{
			@Override
			public void run()
			{
				graphDatabaseInstance.shutdown();
			}
		} );
	}


}
