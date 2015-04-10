package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.services;

import javax.inject.Singleton;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IDBSchemaManager;

/**
 * 
 * @author tejasvamsingh
 *
 */
@Singleton
public class DBSchemaManager implements IDBSchemaManager {

	@Override
	public void setDBSchema(GraphDatabaseService graphDatabaseInstance) {

		System.out.println("Inside setDBSchema in DBManagerSchemaManager");

		try (Transaction tx = graphDatabaseInstance.beginTx()) {

			// schema constraints go here

			graphDatabaseInstance.schema()
					.constraintFor(DynamicLabel.label("User"))
					.assertPropertyIsUnique("username").create();
			graphDatabaseInstance.schema()
					.constraintFor(DynamicLabel.label("User"))
					.assertPropertyIsUnique("email").create();
			graphDatabaseInstance.schema()
					.constraintFor(DynamicLabel.label("Location"))
					.assertPropertyIsUnique("locationName").create();

			tx.success();
		} catch (Exception e) {
			System.out.println("Schema Constraints already defined.");
			return;
		}
	}

}
