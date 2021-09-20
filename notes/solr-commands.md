## Solr operations

### Faceted Search

```
http://localhost:8983/solr/productscatalog/select?facet.field=brand&facet=true&q=*:*&rows=0
```

Response:
```json
{
  "response": {
    "numFound": 14,
    "start": 0,
    "numFoundExact": true,
    "docs": []
  },
  "facet_counts": {
    "facet_queries": {},
    "facet_fields": {
      "brand": [
        "apple",
        6,
        "sony",
        6,
        "logitech",
        2
      ]
    }
  }
}
```