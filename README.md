![Lamp Project Logo](http://www.googledrive.com/host/0B33KzMHyLoH2eVNHWFJZdmthOVk/Lamp-Logo.png)
# 18.20 Plug
## Introduction
This project contains web service functions required for turning the lamps on and off.
![18.20 Plug Architecture](http://aolab.github.io/documentation/architecture/18.20-Plug.jpg)
## How to ...
### Note for eclipse users
Because this project uses oracle sun related java packages it's access by default
is disabled and the autocompletion does not appear.
In order to fix this problem visit
[this](http://stackoverflow.com/questions/13155734/eclipse-cant-recognize-com-sun-net-httpserver-httpserver-package)
stackoverflow question. Second Answer :)

## REST API
### OnI Request [Depricated]
Turn the lamp on for the specified interval

- URL:
  /lamp/OnI
- Method:
  `POST`
- JSON params:

| Parameter  | Value     | Description                      |
|:----------:|:---------:|:-------------------------------- |
| id         | String[2] | the id of the destination lamp   |
| command    | Integer   | the interval in which lamp is on |

### Turn Request
Turn the lamp on or off generally (No interval specified)

- URL:
  /lamp/turn
- Method:
  `POST`
- JSON params:

| Parameter  | Value     | Description                       |
|:----------:|:---------:|:--------------------------------- |
| id         | String[2] | the id of the destination lamp    |
| status     | Boolean   | `true` for on and `false` for off |

- Success response
: Code: 200
- Error response
:

    |   Status codes       |              Description               |
    |:--------------------:|:-------------------------------------- |
    |  400 Bad Request     | The specified parameters are not valid |
    |  404 Not Found       | The specified path was not found       |
    |  501 Not Implemented | The specified method was not valid     |

### Boot Request
Boot the system and connect 18.20-Plug into Kaa

- URL:
  /lamp/boot
- Method:
  `POST`

### Status Request
Get status of Lamp

- URL
: /lamp/status
- Method
: `POST`
- JSON params
:

    | Parameter |   Value  |              Description               |
    |:---------:|:--------:|:-------------------------------------- |
    |     id    |  string  | Target lamp identificaiton             |

- Success response
: Code: 200
- Error response
:

    |   Status codes       |              Description               |
    |:--------------------:|:-------------------------------------- |
    |  400 Bad Request     | The specified parameters are not valid |
    |  404 Not Found       | The specified path was not found       |
    |  501 Not Implemented | The specified method was not valid     |

## Contributors
* [Prof. Bahador Bakhshi](http://ceit.aut.ac.ir/~bakhshis/)
* [Parham Alvani](http://1995parham.github.io/)
* [Iman Tabrizian](https://github.com/Tabrizian)
