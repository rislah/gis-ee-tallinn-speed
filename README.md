# gis-ee-tallinn-speed
> Pulls live data from https://gis.ee/tallinn/ and calculates the distance travelled and speed in relation to the previous GPS coordinates.
> 
Since there's no information in what intervals it pulls live GPS data and how stale the geojson endpoint is, 
then the speed could sometimes be absurdly high.

### Deployment
docker-compose.yml - development environment

k8s - production example of pulling database credentials from HashiCorp Vault