# World Clock

A simple world clock application that displays the current time in various cities around the world.
It uses open-meteo to find city coordinates and Mapbox to display a static map with markers for each 
city. It uses Kastro to calculate current day/night for each city.

## Design

Shamelessly uses the awesome design by Omar Faizan, which can be found 
[here](https://www.behance.net/gallery/81438769/World-Clock-App) and
[here](https://dribbble.com/shots/6559878-World-Clock-App-Kit).

<img width="40%" src="https://github.com/user-attachments/assets/41ae2544-55d8-4ee0-a576-1ca5b27dfe1f" />
<img width="40%" src="https://github.com/user-attachments/assets/236056d8-4761-45f8-a03a-553ad6e2ea10" />


## APIs

### City Geocoding API here

https://geocoding-api.open-meteo.com/v1/search?name=Ber&count=5&language=en&format=json

This returns a JSON response with city information, including latitude, longitude, timezone,
and administrative divisions:

```json
{
  "results": [
    {
      "name": "Berlin",
      "country": "Germany",
      "latitude": 52.52,
      "longitude": 13.405,
      "timezone": "Europe/Berlin",
      "admin1": "State of Berlin",
      "admin2": "Berlin, Stadt",
      "admin3": "Berlin"
    }
  ]
}
```

### Mapbox Mapping API

World Clock uses Mapbox Static Map API to display a static map with markers for each city.

To use Mapbox, you need to sign up for an account and obtain an access token.

Store your Mapbox Access Token in your environment as described
[here](https://docs.mapbox.com/help/troubleshooting/private-access-token-android-and-ios/#non-git-option).

Mapbox Static Map API here:

https://docs.mapbox.com/api/maps/static-images/
https://console.mapbox.com/studio/styles/jesterwrestler/cmcu29qe2006001r47g1k3n4d/edit/#11/40.73/-74

### Sun position API

https://github.com/yoxjames/Kastro

## TODO

* Use GeoKJSON to calculate distance two points in the database
* Add more info to each city:
  * Day/Night timeline
  * 9am/5pm timeline
  * Info for contacts in each city
