# Task list

| Task | ID | Params | Description |
| ---- | -- | ------ | ----------- | 
| Tasks | tasks |  | Show all available tasks in your version of barrister. |
| Help | help | | Show help. |
| Compare | compare | [Configuration file](documentation/CONFIGURATION_FILE.md)| Run tests based on configuration file. |
| Tokenize | tokenize | URL |
| User interface | ui | | Show user interface. |

### Tasks
Show all available tasks in your version of barrister.

### Help
Show help.

### Compare
```bash
barrister compare [OPTIONS] {configuration file}
```



### Tokenize
```bash
barrister tokenize [OPTIONS] {inputfile} {outputfile}
```

| Option | Description |
| ------ | - |
| -l=LANGUAGE | Set used language. Default: Auto-detection |
| -v=VERSION | Set used version of language. Default: Newest version|
