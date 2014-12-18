package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts;

import javax.ejb.Local;

import org.neo4j.graphdb.GraphDatabaseService;
/**
 * 
 * @author tejasvamsingh
 *
 */
@Local
public interface IDBSchemaManager {
	public void setDBSchema(GraphDatabaseService graphDatabaseInstance);

}
