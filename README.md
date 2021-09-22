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


4. This app uses in-memory database (H2) so need to set up any DB


5. Run the Spring Boot application


6. Some pre-configured data gets loaded in DB as well as in Solr after the start-up

   `simple_product_catalog.csv` contains the pre-configured data