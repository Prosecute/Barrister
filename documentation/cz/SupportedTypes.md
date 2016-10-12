# Podporované soubory

## Mody
| Mod | Určen pro | Popis |
| --- | --------- | ----- |
| tokencompare | sourcecode | 
| validate | sourcecode, text, binary | 
| glossarycompare | sourcecode, text |

### Porovnávání tokenů (tokencompare)
Toto porovnávání je v Barristeru velmi specifické, je založeno na GSTiling algoritmu, ale celý algoritmus byl poupraven na základě analýzy po několik let sbíraných zdrojových kódů studentů a jejich pokusů o plagiáty. 

Původní GSTiling algortimus, používaný v JPlag a pravděpodobně i Moss (na základě téměř stejných výsledků v částech kde se GSTiling chová specificky narozdíl od jíných) totíž nedokáže odhalit duplicitní kód v případě kdy se v souboru vyskytuje vícekrát. Tudíž pro zmatení stačilo okopírovat celou práci ale vytvořit ji 3x a z téměř 100% shody byly shoda okolo 41%.

## Programovací jazyky (Sourcecode)
| Jazyk | Verze | Codename | Stav | Podporované mody |
| ----- | ----- | -------- | ---- | ---------------- |
| Java | 1.8 (a předchozí) | java:1.8 | Podporováno | tokencompare, validate, glossarycompare |
*Codename = jazyk:verze