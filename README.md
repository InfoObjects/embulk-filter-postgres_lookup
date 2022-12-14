
<p align="center">
  <a href="https://www.infoobjects.com/" target="blank"><img src="screenshots/logo.png" width="150" alt="InfoObjects Logo" /></a>
</p>
<p align="center">Infoobjects is a consulting company that helps enterprises transform how and where they run applications and infrastructure.
From strategy, to implementation, to ongoing managed services, Infoobjects creates tailored cloud solutions for enterprises at all stages of the cloud journey.</p>

# Postgres lookup filter plugin for Embulk
[![license](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

​
An Embulk filter plugin for Lookup Transformation with Postgres database
​
- **postgres_lookup**: Required attributes for the LookUp Filter Plugin -
    - **driver_path**: driver path for your driver which you installed in your system (example `C:/Users/Abhishek Gupta/Desktop/postgresql-9.0-801.jdbc4.jar`)
    - **driver_class**: driver class name (example `org.postgresql.Driver`)
    - **schema_name**: schema name (example `testSchema`)
    - **host**: database host (example `localhost`) (required)
    - **port**: database port (example port for postgres `1433`) (required)
    - **database**: database name (required)
    - **tablename**: table name of your database (required)
    - **username**: username for your database (required)
    - **password**: password for database (required)
    - **mapping_from**: (Name of columns to be matched with table 2 columns) (required)
        - **Name of column-1**: column name-1 from input file
        - **Name of column-2**: column name-2 from input file etc ...
    - **mapping_to**:   (Name of columns to be matched with table 1 columns) (required)
        - **Name of column-1**: column name-1 from input file
        - **Name of column-2**: column name-2 from input file
    - **new_columns**:   (New generated column names) (required)
        - **Name-1,Type-1**: Any Name, Type of the name (name: country_name, type: string)
        - **Name-2,Type-2**: Any Name, Type of the name (name: country_address, type: string) etc ...
## Example - columns
​
Input1 for table 1 is as follows :-
​
```
    year               country_code                 country_name            literacy_rate
    
    1990                    1                          India                       80%
    1993                    2                           USA                        83%
    1997                    3                          JAPAN                        
    1999                    4                          China                       72%
    2000                    5                         Ukraine                      68%
    2002                    6                          Italy                       79%
    2004                    7                            UK                        75%
    2011                    8                           NULL                       42%
```
​
Input2 for table 2 is as follows :-
​
```
    id               country_population                        country_address               country_GDP
    
    1                       11.3                                    India                       1.67
    2                       18.2                                     USA                        16.72
    3                       30                                      JAPAN                       5.00
    4                       4                                       China                       9.33
    5                       57                                     Ukraine                      1.08
    6                       63                                      Italy                       2.068
    7                       17                                       UK                         2.49
    8                       28                                       UAE                        1.18                            
    
    
    Note: country_population is calculated in Billion and country_GDP is calculated in $USD Trillion
```
​
As shown in yaml below, columns mentioned in mapping_from will be mapped with columns mentioned in mapping_to      
ie:

​
country_code : id                       
country_name : country_address

After successful mapping an Output.csv file containing the columns mentioned in new_columns will be generated              
​
​

Output File generated :-
​
```
    year               country_code                 country_name              literacy_rate                 country_GDP                   country_population
    
    1990                    1                          India                       80%                         1.67                                11.3
    1993                    2                           USA                        83%                         16.72                               18.2
    1997                    3                          JAPAN                                                   5.00                                30
    1999                    4                          China                       72%                         9.33                                4
    2000                    5                         Ukraine                      68%                         1.08                                57
    2002                    6                          Italy                       79%                         2.068                               63
    2004                    7                            UK                        75%                         2.49                                17
    2011                    8                           NULL                       42%                                                         
```
​
​
​
```yaml
- type: postgress_lookup
    lookup_destination: mysql
    host: localhost
    port: 5432
    database: Abhishek
    tablename: country
    username: postgres
    password: root
    mapping_from:
      - quarter_number
      - attr_1
    mapping_to:
      - id
      - country_address
    new_columns:
      - { name: country_name, type: string }
      - { name: country_address, type: string }
```
​
Notes:
1. mapping_from attribute should be in same order as mentioned in input file.
   ​
## Development
​
Run example:
​
```
$ ./gradlew package
$ embulk run -I ./lib seed.yml
```
​
Deployment Steps:
​
```
Install ruby in your machine
$ gem install gemcutter (For windows OS)
​
$ ./gradlew gemPush
$ gem build NameOfYourPlugins (example: embulk-filter-postgress_lookup)
$ gem push embulk-filter-postgress_lookup-0.1.0.gem (You will get this name after running above command)
```
​
​
Release gem:
​
```
$ ./gradlew gemPush
```
## Licensing

InfoObjects [license](LICENSE) (MIT License)