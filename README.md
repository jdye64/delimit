delimit
=======

It is never fun to deal with raw delimited text. This is a simple library for in memory delimited text manipulations. The goal is to provide the loading and persisting of the processed manipulated text while you provide the service that performs the desired operations on the data. All data is loaded into a GenericContext which relies on the first line of the file containing the column headers. Each row of the file is placed into the GenericContext as a GenericRow and all of the column header value are made available in a HashMap so that they can be easily retrieved and altered.
