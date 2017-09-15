#!/bin/bash
read line

IFS=',' read -r -a array <<< "$line"

index_id=${array[0]}
function=${array[1]}
g6=${array[2]}
ordem=${array[3]}
grauminimo=${array[4]}
graumaximo=${array[5]}
trianglefree=${array[6]}
conexo=${array[7]}
bipartite=${array[8]}
parameter_id=${array[9]}
caixa1=${array[10]}
adjacency=${array[11]}
laplacian=${array[12]}
slaplacian=${array[13]}
allowdiscgraphs=${array[14]}
biptonly=${array[15]}
maxresults=${array[16]}
adjacencyb=${array[17]}
laplacianb=${array[18]}
slaplacianb=${array[19]}
chromatic=${array[20]}
chromaticb=${array[21]}
click=${array[22]}
clickb=${array[23]}
largestdegree=${array[24]}
numedges=${array[25]}
runGeni=${array[26]}
runEigsolve=${array[27]}
serial=${array[28]}

workdir="/home/magno/riographx"
graphsdir="$workdir/grafos/$serial"
sourceg6="$graphsdir/graph.g6"

mkdir -p "$graphsdir"

echo "$g6" > "$sourceg6"

sage -c 'load("'"$workdir/geni.py"'");geni("'"$graphsdir/"'","'"$sourceg6"'","'"-a -b -c -d -e 3 -g"'")'

sage -c 'load("'"$workdir/Eigenvalue.py"'");Eigenvalue("'"$graphsdir/"'","'"$sourceg6"'","'"-a -q -l -x -y -z"'")'

#function_new=${function//[[:blank:]]/}
java -jar $workdir/evaluate.jar "$graphsdir/" "$function" "$index_id" "$maxresults" "$caixa1" "$g6" "$ordem" "$grauminimo" "$graumaximo"
 
#	return index_id + "," + function + "," + g6 + "," + ordem + "," + grauminimo + "," + graumaximo+ "," +trianglefree+ "," +conexo+ "," +
#		bipartite+ "," +parameter_id+ "," +caixa1+ "," +adjacency+ "," +laplacian+ "," +slaplacian+ "," +allowdiscgraphs+ "," +
#		biptonly+ "," +maxresults+ "," +adjacencyb+ "," +laplacianb+ "," +slaplacianb+ "," +chromatic+ "," +chromaticb+ "," +click+ "," +
#		clickb+ "," +largestdegree+ "," +numedges+","+runGeni+","+runEigsolve+","+serial;



