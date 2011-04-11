package com.kenshoo.liquibase;

import liquibase.changelog.ChangeSet;
import liquibase.database.Database;
import liquibase.diff.Diff;
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

    void update(String contexts) throws LiquibaseException;

    void update(int changesToApply, String contexts) throws LiquibaseException;

    void rollback(int changesToRollback, String contexts) throws LiquibaseException;

    void rollback(String tagToRollBackTo, String contexts) throws LiquibaseException;

    void rollback(Date dateToRollBackTo, String contexts) throws LiquibaseException;

    void changeLogSync(String contexts) throws LiquibaseException;

    void markNextChangeSetRan(String contexts) throws LiquibaseException;

    void dropAll() throws DatabaseException, LockException;

    void dropAll(String... schemas) throws DatabaseException;

    void tag(String tagString) throws LiquibaseException;

    void updateTestingRollback(String contexts) throws LiquibaseException;

    void forceReleaseLocks() throws LiquibaseException;

    List<ChangeSet> listUnrunChangeSets(String contexts) throws LiquibaseException;

    void reportStatus(boolean verbose, String contexts, Writer out) throws LiquibaseException;

    void clearCheckSums() throws LiquibaseException;

    void generateDocumentation(String outputDirectory) throws LiquibaseException;

    void generateDocumentation(String outputDirectory, String contexts) throws LiquibaseException;

    Diff diff(Database referenceDatabase, Database targetDatabase);

    void validate() throws LiquibaseException;

    public boolean isSafeToRunMigration() throws DatabaseException;
}
