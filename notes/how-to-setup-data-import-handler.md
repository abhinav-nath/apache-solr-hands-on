## Steps to setup Solr Data Import Handler (DIH) for PostgreSQL

[Config files](./dih_config_files "Config Files") used in this example

1. Create a (sample) table in MySQL:

   ```sql
   CREATE TABLE products
   (id BIGINT NOT NULL AUTO_INCREMENT,
    name TEXT NOT NULL,
    category TEXT NOT NULL,
    brand TEXT NOT NULL,
    description TEXT NOT NULL,
    color TEXT,
    price DOUBLE NOT NULL,
    date_added TEXT NOT NULL,
    in_stock BOOLEAN NOT NULL,
   PRIMARY KEY (id));
   ```

2. Add a sample record:

   ```sql
   INSERT INTO products ("id", "name", "category", "brand", "description", "color", "price", "date_added", "in_stock") VALUES
   (1, 'Macbook Pro', 'ELECTRONICS', 'Apple', 'Apple Macbook Pro 2019 model', 'RED', 2200, '2021-09-17', 1);
   ```

3. Create a new core:

   ```
   $ ./solr create -c productscatalog
   ```

   It should be visible in the solr admin dashboard:

   ![Solr Admin Dashboard](./images/solr_admin_new_core.png "New Core in Solr Admin Dashboard")


4. Go to the `conf` directory in the newly created core:

   ```
   cd $SOLR_HOME/server/solr/productscatalog/conf
   ```

   Three files are important for us:

   - solrconfig.xml
   - data-config.xml
   - schema.xml


5. Download JDBC driver for MySQL from [here](https://dev.mysql.com/downloads/connector/j/ "MySQL JDBC driver")

   Place the jar file `mysql-connector-java-*.jar` at:

   ```shell
   $SOLR_HOME/contrib/dataimporthandler/lib
   ```

   ```shell
   $ ls -lrt
   total 2048
   -rw-r--r--@ 1 abhinavnath  staff  1005522 Sep 17 22:08 mysql-connector-java-*.jar
   ```

6. `solrconfig.xml` changes:

   - Add below `lib` paths:
     ```xml
     <config>
       ...
       ...
       <lib dir="${solr.install.dir:../../../..}/contrib/dataimporthandler/lib" regex=".*\.jar" />
       <lib dir="${solr.install.dir:../../../..}/dist/" regex="solr-dataimporthandler-.*\.jar" />
       ...
       ...
     </config>
     ```

   - Add a new `requestHandler`:
     ```xml
     <!-- A request handler for productscatalog data import handler -->
     <requestHandler name="/dataimport" class="org.apache.solr.handler.dataimport.DataImportHandler">
       <lst name="defaults">
         <str name="config">data-config.xml</str>
       </lst>
     </requestHandler>
     ```


7. Create `data-config.xml` file:

   ```xml
   <dataConfig>
     <dataSource type="JdbcDataSource" name="productscatalog" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost:3306/productscatalog-db" user="root" password="password" />
     <document name="products_catalog">
       <entity name="products" query="SELECT * FROM products;">
         <field column="id" name="id" />
         <field column="name" name="name" />
         <field column="category" name="category" />
         <field column="brand" name="brand" />
         <field column="description" name="description" />
         <field column="color" name="color" />
         <field column="price" name="price" />
         <field column="date_added" name="dateAdded" />
         <field column="in_stock" name="inStock" />
       </entity>
     </document>
   </dataConfig>
   ```


8. Rename `managed-schema` file to `schema.xml`

   ```shell
   $ mv managed-schema schema.xml
   ```

   Add the fields that we mapped in `data-config.xml`:

   ```xml
   <schema name="default-config" version="1.6">
     ...
     ...
     <field name="id" type="string" indexed="true" stored="true" required="true" multiValued="false" />
     <field name="name" type="string" indexed="true" stored="true" />
     <field name="category" type="string" indexed="true" stored="true" />
     <field name="brand" type="string" indexed="true" stored="true" />
     <field name="description" type="string" indexed="true" stored="true" />
     <field name="color" type="string" indexed="true" stored="true" />
     <field name="price" type="pdoubles" indexed="true" stored="true" />
     <field name="dateAdded" type="string" indexed="true" stored="true" />
     <field name="inStock" type="booleans" indexed="true" stored="true" />
     ...
     ...
   </schema>
   ```


9. Start Solr

   ```shell
   $ ./solr start
   ```


10. Use `DataImport` option to index the data

    - Go to Solr Admin Dashboard
    - Go to `DataImport` option and choose the `full-import` command from the drop-down
    - Hit `Execute`

      <img src="images/dataimport.png" alt="DataImport Execute" style="height: 460px; width:326px;"/>

    - DataImport should successfully index one document

      ![Indexing completed](./images/dataimport_indexing_completed.png "Indexing completed")


11. Query the newly indexed document using the `Request-Handler`

    ![Request Handler](./images/request_handler_query.png "Request Handler")