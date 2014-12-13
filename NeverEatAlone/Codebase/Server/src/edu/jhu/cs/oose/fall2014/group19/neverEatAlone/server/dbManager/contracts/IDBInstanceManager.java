package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts;

import javax.ejb.Local;

import org.neo4j.graphdb.GraphDatabaseService;

@Local
public interface IDBInstanceManager {
	public GraphDatabaseService getGraphDatabaseInstance();
}
