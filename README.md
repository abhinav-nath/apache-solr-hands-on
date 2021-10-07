## Exploring Solr using Spring Data Solr

- [About Apache Solr](./notes/apache-solr.md "About Apache Solr")
- [How to set up Data Import Handler](./notes/how-to-setup-data-import-handler.md "How to set up DIH")

---
#### Steps to run this application:

1. Download and install Solr in local


2. Start Solr
   ```shell
   $ ./solr start
   ```

   Solr Admin Dashboard link for local:

   http://localhost:8983/solr/#/


3. Create a new Core/Collection:
   ```shell
   $ ./solr create -c productscatalog
   ```


4. This app uses MySQL DB. Command to run MySQL Docker container in local:

   ```shell
   $ docker run --name mysqldb -e MYSQL_ROOT_PASSWORD=password -v $HOME/mysql-data:/var/lib/mysql -p 3306:3306 -d mysql
   ```


5. Run the Spring Boot application


6. Some pre-configured data gets loaded in DB as well as in Solr after the start-up

   `simple_product_catalog.csv` contains the pre-configured data