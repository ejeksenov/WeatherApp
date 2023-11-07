package kz.lidercargo.weatherapp.domain.model


data class CityData(
    val name: String,
    val latLon: String,
    var isSelected: Boolean = false
)


val cities = mutableListOf<CityData>(
    CityData(
        name = "Almaty",
        latLon = "43.2775,76.8958"
    ),
    CityData(
        name = "Shymkent",
        latLon = "42.3167,69.5958"
    ),
    CityData(
        name = "Astana",
        latLon = "51.1472,71.4222"
    ),
    CityData(
        name = "Qaraghandy",
        latLon = "49.8028,73.1056"
    ),
    CityData(
        name = "Aqtobe",
        latLon = "50.2836,57.2297"
    ),
    CityData(
        name = "Taraz",
        latLon = "42.9000,71.3667"
    ),
    CityData(
        name = "Pavlodar",
        latLon = "52.3000,76.9500"
    ),
    CityData(
        name = "Semey",
        latLon = "50.4333,80.2667"
    ),
    CityData(
        name = "Oskemen",
        latLon = "49.9833,82.6167"
    ),
    CityData(
        name = "Atyrau",
        latLon = "47.1167,51.8833"
    ),
    CityData(
        name = "Qyzylorda",
        latLon = "44.8500,65.5167"
    ),
    CityData(
        name = "Oral",
        latLon = "51.2225,51.3725"
    ),
    CityData(
        name = "Qostanay",
        latLon = "53.2000,63.6200"
    ),
    CityData(
        name = "Petropavl",
        latLon = "54.8833,69.1667"
    ),
    CityData(
        name = "Temirtau",
        latLon = "50.0500,72.9500"
    ),
    CityData(
        name = "Aqtau",
        latLon = "43.6525,51.1575"
    ),
    CityData(
        name = "Kokshetau",
        latLon = "53.2833,69.3833"
    ),
    CityData(
        name = "Turkistan",
        latLon = "43.3019,68.2692"
    ),
    CityData(
        name = "Taldyqorgan",
        latLon = "45.0167,78.3667"
    ),
    CityData(
        name = "Zhezqazgan",
        latLon = "47.7833,67.7000"
    ),
).apply {
    sortBy { it.name }
}