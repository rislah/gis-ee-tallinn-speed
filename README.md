# gis-ee-tallinn-speed
> Pulls live data from https://gis.ee/tallinn/ and calculates the distance travelled and speed using PostGis.

Since there's no information how fast it gets live GPS coordinates (besides the geojson endpoint refreshing with new data every 5 seconds), then the speed could sometimes be absurdly high.

### Deployment
docker-compose.yml - development environment

k8s - production example of pulling database credentials from HashiCorp Vault
