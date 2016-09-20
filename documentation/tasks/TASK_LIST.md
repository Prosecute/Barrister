# Task list

| Task | ID | Params | Description |
| ---- | -- | ------ | ----------- | 
| Tasks | tasks | Ø | Show all available tasks in your version of barrister. |
| Help | help | [Task](documentation/tasks/TASK_LIST.md) | Show help. |
| Compare | compare | [Configuration file](documentation/CONFIGURATION_FILE.md)| Run tests based on configuration file. |
| Tokenize* | tokenize | URL | Convert input file to tokens. | 
| User interface* | ui | Ø | Show user interface. |
\* Not implemented
\** Experimental

### Tasks
```bash
barrister tasks
```
Show all available tasks in your version of barrister.

### Help
```bash
barrister help {task}
```
Show help.

| Param | Optional | Description |
| ----- | -------- | ----------- |
| {task} | Yes | Show detailed info about specified task. |

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
