/ [Testing](../../../../README.MD) / [Languages](../../../README.MD) / [Java](../../README.MD) / [Integration tests](../README.MD) / **Test 2**

# Test 2
||
|-|
|1. [Content](#content)|
|2. [Match table](#match-table)|

## Content
| ID | Submission | Content |
| -- | ---------- | ------- |
| 0 | submission0 | Empty file |
| 1 | submission1 | Empty class|
| 2 | submission2 | Class with empty methods and unassigned globals variables|
| 3 | submission3 | Normal (filled) class |
| 4 | submission4 | Normal (filled) class, same as above |
| 5 | submission5 | Normal (filled) class, with different implementation |
| 6 | submission6 | Class logic splitted to two classes, one extends other one |
| 7 | submission7 | Class logic splitted to two classes, one containing other |

## Match table
### Expected result range
|  \  |  0  |  1  |  2  |  3  |  4  |  5  |  6  |  7  |
| :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: |
|**0**|  \  |  -% |
|**1**|  -% |  \  |
|**2**|  -% |  -% |  \  |
|**3**|  -% |  -% |  -% |  \  | 100%|
|**4**|  -% |  -% |  -% | 100%|  \  |
|**5**|
|**6**|
|**7**|

### Average result in Barrister
