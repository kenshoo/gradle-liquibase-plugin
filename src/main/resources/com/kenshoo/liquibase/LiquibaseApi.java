package com.kenshoo.liquibase;

import liquibase.changelog.ChangeSet;
import liquibase.database.Database;
import liquibase.diff.Difference;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.LockException;

import java.io.Writer;
import java.util.Date;
import java.util.List;

/**
 * The interface we expose to the plugin
 */
public interface LiquibaseApi {

    /**
     * Applying all changesets with given contexts to the db
     *
     * @param contexts
     * @throws LiquibaseException
     */
    void update(String contexts) throws LiquibaseException;

    /**
     * Writing SQL queries for all changesets with given contexts to the db
     *
     * @param contexts
     * @param out
     * @throws LiquibaseException
     */
    void update(String contexts, StringWriter out) throws LiquibaseException;

    /**
     * Applying all changesets with given contexts to the db
     *
     * @param changesToApply
     * @param contexts
     * @throws LiquibaseException
     */
    void update(Integer changesToApply, String contexts) throws LiquibaseException;

    /**
     * Rolling back changesets according to number, tag or date.
     *
     * @param changesToRollback
     * @param contexts
     * @throws LiquibaseException
     */
    void rollback(Integer changesToRollback, String contexts) throws LiquibaseException;

    /**
     * Rolling back changesets according to number, tag or date.
     *
     * @param tagToRollBackTo
     * @param contexts
     * @throws LiquibaseException
     */
    void rollback(String tagToRollBackTo, String contexts) throws LiquibaseException;

    /**
     * Rolling back changesets according to number, tag or date.
     *
     * @param dateToRollBackTo
     * @param contexts
     * @throws LiquibaseException
     */
    void rollback(Date dateToRollBackTo, String contexts) throws LiquibaseException;

    /**
     * Mark all changes as executed in the database.
     *
     * @param contexts
     * @throws LiquibaseException
     */
    void changeLogSync(String contexts) throws LiquibaseException;

    /**
     * Mark the next change set as executed in the database.
     *
     * @param contexts
     * @throws LiquibaseException
     */
    void markNextChangeSetRan(String contexts) throws LiquibaseException;

    /**
     * Drops all database objects owned by the current user or by given schemas
     *
     * @throws DatabaseException
     * @throws LockException
     */
    void dropAll() throws DatabaseException, LockException;

    /**
     * Drops all database objects owned by the current user or by given schemas
     *
     * @param schemas
     * @throws DatabaseException
     */
    void dropAll(String... schemas) throws DatabaseException;

    /**
     * 'Tags' the current database state for future rollback
     *
     * @param tagString
     * @throws LiquibaseException
     */
    void tag(String tagString) throws LiquibaseException;

    /**
     * Running update rollback and update again (for testing)
     *
     * @param contexts
     * @throws LiquibaseException
     */
    void updateTestingRollback(String contexts) throws LiquibaseException;

    /**
     * Releases all locks on the database changelog
     *
     * @throws LiquibaseException
     */
    void forceReleaseLocks() throws LiquibaseException;

    /**
     * List all un-run changes sets
     *
     * @param contexts
     * @return
     * @throws LiquibaseException
     */
    List<ChangeSet> listUnrunChangeSets(String contexts) throws LiquibaseException;

    /**
     * Outputs count (list if –verbose) of unrun change sets
     *
     * @param verbose
     * @param contexts
     * @param out
     * @throws LiquibaseException
     */
    void reportStatus(Boolean verbose, String contexts, StringWriter out) throws LiquibaseException;

    /**
     * Removes current checksums from database. On next run checksums will be recomputed
     *
     * @throws LiquibaseException
     */
    void clearCheckSums() throws LiquibaseException;

    /**
     * Generates Javadoc-like documentation based on current database and change log
     *
     * @param outputDirectory
     * @throws LiquibaseException
     */
    void generateDocumentation(String outputDirectory) throws LiquibaseException;

    /**
     * Generates Javadoc-like documentation based on current database and change log
     *
     * @param outputDirectory
     * @param contexts
     * @throws LiquibaseException
     */
    void generateDocumentation(String outputDirectory, String contexts) throws LiquibaseException;

    /**
     * Writes description of differences (between two db's) to standard out
     *
     * @param referenceDatabase
     * @param targetDatabase
     * @return
     */
    Difference diff(Database referenceDatabase, Database targetDatabase);

    /**
     * Checks the changelog for errors
     *
     * @throws LiquibaseException
     */
    void validate() throws LiquibaseException;

    /**
     * Returns true if it is "save" to migrate the database.
     * Currently, "safe" is defined as running in an output-sql mode or against a database on localhost.
     * It is fine to run Liquibase against a "non-safe" database, the method is mainly used to determine if the user
     * should be prompted before continuing.
     *
     * @return
     * @throws DatabaseException
     */
//    public boolean isSafeToRunMigration() throws DatabaseException;
}
