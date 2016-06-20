

```yaml
Submissions:
 - Path: ...
   Direct: true
   CompareOnly: true
 - Path: ...
   Exclusive:
    - ...
   Submission:
     Path: ...
     Exclude:
      - src/test/**/*.java
Trials:
 - Language: Java
   Version: 1.7
   Extension:
    - .java



   Basecode:
    - Path: ... # URL
      Extension:
       - .jav
         .java
      Exclude:
       - Test.java
         Te??.java
         *st.java
 -
```