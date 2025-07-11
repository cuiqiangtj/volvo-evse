# 一.项目概述

作为一个充电服务商(eMSP),构建一个充电站的管理系统,维护充电站点运营商提供的数据,并作为客户端提供APi接口.

1.  Location(充电站点),包含属性name(名称), address(地址), coordinates(坐标), business hours(营业时间)等
2.  EVSE (电动车辆供电设备):,包含属性,唯一标识和运行状态等
3.  Connector(EVSE充电连接器):包含设备的参数: standard标准, power level功率,  voltage电压等

# 二.功能设计

### 1.维护Location信息

维护充电站点信息,提供新增与修改接口.

### 2.维护EVSE信息

主要维护EVSE信息并通过调用接口修改设备状态.提供新增,修改信息,维护状态,查询等接口

新增修改时候需要判断设备标识是否符合格式,若是不符合需返回400错误

\<CountryCode>*\<PartyID>*\<LocalEVSEID>
CountryCode: A 2-letter ISO 3166 alpha-2 code (e.g., "US", "NL", "CN")
PartyID: A 3-character alphanumeric code assigned to the operator (e.g., "ABC")
LocalEVSEID: A unique string (up to 30 characters) scoped within the operator
Example\:US*ABC*EVSE123456

修改状态需要判断状态变化,状态分为

AVAILABLE: Ready to be used
BLOCKED: Reserved or temporarily unavailable
INOPERATIVE: Out of service due to malfunction or maintenance
REMOVED: Permanently decommissioned (terminal state)

状态转换规则:
INITIAL → AVAILABLE
AVAILABLE ↔ BLOCKED
AVAILABLE ↔ INOPERATIVE
Any state → REMOVED (irreversible)

### 3.维护Connector信息

主要维护充电连接器的设备参数,提供新增与修改的接口


# 三.数据库设计

| 表名称      | 字段名称            | 数据类型         | 注释   | 数据说明  |
| :------- | :-------------- | :----------- | :--- | :---- |
| location | ID              | int(11)      | id   | 主键,自增 |
| location | NAME            | varchar(100) | 名称   |       |
| location | ADDRESS         | varchar(100) | 地址   |       |
| location | COORDINATES     | varchar(100) | 坐标   |       |
| location | BUSINESS\_HOURS | varchar(100) | 营业时间 |       |
| location | CREATE\_TIME    | timestamp    | 创建时间 |       |
| location | UPDATE\_TIME    | timestamp    | 修改时间 |       |
|          |                 |              |      |       |

| 表名称  | 字段名称         | 数据类型         | 注释            | 数据说明  |
| :--- | :----------- | :----------- | :------------ | :---- |
| evse | ID           | int(11)      | id            | 主键,自增 |
| evse | LOCATION\_ID | int(11)      | 关联location表id |       |
| evse | EVSE\_CODE   | varchar(100) | 充电器唯一标识       |       |
| evse | STATUS       | varchar(100) | 充电器状态         |       |
| evse | CREATE\_TIME | timestamp    | 创建时间          |       |
| evse | UPDATE\_TIME | timestamp    | 修改时间          |       |
|      |              |              |               |       |

| 表名称       | 字段名称         | 数据类型         | 注释        | 数据说明  |
| :-------- | :----------- | :----------- | :-------- | :---- |
| connector | ID           | int(11)      | id        | 主键,自增 |
| connector | EVSE\_ID     | int(11)      | 关联evse表id |       |
| connector | STANDARD     | varchar(100) | 标准        |       |
| connector | POWER\_LEVEL | varchar(100) | 功率        |       |
| connector | VOLTAGE      | varchar(100) | 电压        |       |
| connector | CREATE\_TIME | timestamp    | 创建时间      |       |
| connector | UPDATE\_TIME | timestamp    | 修改时间      |       |
|           |              |              |           |       |

# 四.接口设计

### 1.新增Location

**请求方法**:POST

**URL**:/location/addLocation

**请求参数**:

| 参数名称          | 类型     | 必填 | 默认值 | 描述   |
| :------------ | :----- | :- | :-- | :--- |
| name          | string | -  | -   | 名称   |
| coordinates   | string | -  | -   | 地址   |
| businessHours | string | -  | -   | 营业时间 |
| address       | string | -  | -   | 坐标   |

**返回值示例**:

```
{"id":3,"name":"testLocation","address":"XX市XX区XX路1号","coordinates":"124.12345,56.12345","businessHours":"0-24","createTime":"2025-07-11T03:31:09.000+00:00","updateTime":"2025-07-11T03:31:09.000+00:00"}
```
### 2.修改Location

**请求方法**:POST

**URL**:/location/updateLocation

**请求参数**:

| 参数名称          | 类型     | 必填 | 默认值 | 描述   |
| :------------ | :----- | :- | :-- | :--- |
| id          | int | -  | -   | 名称   |
| name          | string | -  | -   | 名称   |
| coordinates   | string | -  | -   | 地址   |
| businessHours | string | -  | -   | 营业时间 |
| address       | string | -  | -   | 坐标   |

**返回值示例**:

```
{"id":1,"name":"testLocation","address":"XX市XX区XX路2号","coordinates":"124.12345,56.12345","businessHours":"9-24","createTime":"2025-07-10T22:55:52.000+00:00","updateTime":"2025-07-11T03:33:16.000+00:00"}
```

### 3.新增EVSE

**请求方法**:POST

**URL**:/evse/addEVSE

**请求参数**:

| 参数名称          | 类型     | 必填 | 默认值 | 描述   |
| :------------ | :----- | :- | :-- | :--- |
| evseCode          | string | -  | -   | 充电器唯一标识   |
| locationId          | int | -  | -   | 关联location的id   |

**返回值示例**:

```
{"id":2,"evseCode":"US*ABC*EVSE123456","status":"Available","locationId":1,"createTime":"2025-07-10T19:47:09.000+00:00","updateTime":"2025-07-10T19:47:09.000+00:00"}
```

### 4.修改EVSE

**请求方法**:POST

**URL**:/evse/updateEVSE

**请求参数**:

| 参数名称          | 类型     | 必填 | 默认值 | 描述   |
| :------------ | :----- | :- | :-- | :--- |
| id          | int | -  | -   | 名称   |
| evseCode          | string | -  | -   | 充电器唯一标识   |
| locationId          | int | -  | -   | 关联location表id   |

**返回值示例**:

```
{"id":1,"evseCode":"US*ABC*EVSE654321","status":"Blocked","locationId":1,"createTime":"2025-07-10T15:38:02.000+00:00","updateTime":"2025-07-10T19:48:34.000+00:00"}
```

### 5.修改EVSE状态

**请求方法**:POST

**URL**:/evse/changeStatusEVSE

**请求参数**:

| 参数名称          | 类型     | 必填 | 默认值 | 描述   |
| :------------ | :----- | :- | :-- | :--- |
| id          | int | -  | -   | 名称   |
| status          | string | -  | -   | 状态(AVAILABLE,BLOCKED,INOPERATIVE,REMOVED)   |

**返回值示例**:

```
The status is chenge.
```

### 5.查询EVSE列表

**请求方法**:GET

**URL**:/evse/queryEVSE

**请求参数**:

| 参数名称          | 类型     | 必填 | 默认值 | 描述   |
| :------------ | :----- | :- | :-- | :--- |
| pageNum          | int | -  | 1   | 查询页序号   |
| pageSize          | int | -  | 10   | 每页大小   |

**返回值示例**:

```
{"total":2,"list":[{"id":1,"evseCode":"US*ABC*EVSE654321","status":"Available","locationId":1,"createTime":"2025-07-10T15:38:02.000+00:00","updateTime":"2025-07-10T19:51:51.000+00:00"},{"id":2,"evseCode":"US*ABC*EVSE123456","status":"Available","locationId":1,"createTime":"2025-07-10T19:47:09.000+00:00","updateTime":"2025-07-10T19:47:09.000+00:00"}],"pageNum":1,"pageSize":2,"size":2,"startRow":0,"endRow":1,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1}
```

### 6.新增Controller

**请求方法**:POST

**URL**:/connector/addConnector

**请求参数**:

| 参数名称          | 类型     | 必填 | 默认值 | 描述   |
| :------------ | :----- | :- | :-- | :--- |
| evseId          | int | -  | -   | evse的id   |
| standard          | string | -  | -   | 标准   |
| powerLevel          | int | -  | -   | 功率   |
| voltage          | int | -  | -   | 电压   |
**返回值示例**:

```
{"id":2,"standard":"GBI17625.1-2012","powerLevel":"100-240VAC","voltage":"200W","evseId":1,"createTime":"2025-07-10T19:57:10.000+00:00","updateTime":"2025-07-10T19:57:10.000+00:00"}
```

### 6.修改Controller

**请求方法**:POST

**URL**:/connector/updateConnector

**请求参数**:

| 参数名称          | 类型     | 必填 | 默认值 | 描述   |
| :------------ | :----- | :- | :-- | :--- |
| id          | int | -  | -   | id   |
| evseId          | int | -  | -   | evse的id   |
| standard          | string | -  | -   | 标准   |
| powerLevel          | int | -  | -   | 功率   |
| voltage          | int | -  | -   | 电压   |
**返回值示例**:

```
{"id":1,"standard":"GBI17625.1-2013","powerLevel":"100-240VAC","voltage":"200W","evseId":1,"createTime":"2025-07-10T16:04:19.000+00:00","updateTime":"2025-07-10T19:57:54.000+00:00"}
```