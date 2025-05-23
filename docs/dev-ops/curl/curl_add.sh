curl -X PUT "117.72.36.124:9200/xfg_dev_tech.user_order/_mapping" -H 'Content-Type: application/json' -d'
{
  "properties": {
    "_sku_name": {
      "type": "text"
    }
  }
}'