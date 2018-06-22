| Branch | Status |
| ------| -----------|
| dev   | [![Build Status](https://semaphoreci.com/api/v1/projects/24306366-4641-4e73-9155-4069ee3aa0b4/1914957/badge.svg)](https://semaphoreci.com/ahk58-31/optkos_backend) |
| master | [![Build Status](https://semaphoreci.com/api/v1/projects/24306366-4641-4e73-9155-4069ee3aa0b4/1913299/badge.svg)](https://semaphoreci.com/ahk58-31/optkos_backend) |

# Optkos Backend

Business logic and database communication for the Optkos application

## Getting Started

To access the db2 database you need a valid account and a working internet connection

### Prerequisites

```
Java 1.8
```

### Installing

Locate the folder containing the .jar file and launch it as specified below

```
java -jar optkos_backend [db2_username] [db2_password]
```

Once the db2 login data has been set you can launch the jar without arguments

```
java -jar optkos_backend
```

After successful execution the application should look like this:

![Alt text](terminal_backend.png?raw=true)

### API Access

The application will launch 3 endpoints

+ http://localhost:1337/AdministrativeApi

+ http://localhost:1338/AppointmentApi

+ http://localhost:1339/StatisticApi


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management


## Versioning

We use [Git](http://git-scm.com/) for versioning. For the versions available please request access from the authors. 

## Authors

* Ali Kaya
* Andre Rehle
* Felix Eisenmann
* Johannes Börmann
* Michael Szostak
* Nina Leveringhaus
* Patrick Handreck

## Acknowledgments

* Made possible by stackoverflow
* Powered by Nullpointer Exceptions


