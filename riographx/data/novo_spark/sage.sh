#!/bin/bash

while read tline; do

	tempstring=${tline#"("}
	line=${tempstring%")"}

	IFS=',' read -r -a array <<< "$tline"

	rddkey=${array[0]}

	index_id=${array[1]}
	function=${array[2]}
	g6=${array[3]}
	ordem=${array[4]}
	grauminimo=${array[5]}
	graumaximo=${array[6]}
	trianglefree=${array[7]}
	conexo=${array[8]}
	bipartite=${array[9]}
	parameter_id=${array[10]}
	caixa1=${array[11]}
	adjacency=${array[12]}
	laplacian=${array[13]}
	slaplacian=${array[14]}
	allowdiscgraphs=${array[15]}
	biptonly=${array[16]}
	maxresults=${array[17]}
	adjacencyb=${array[18]}
	laplacianb=${array[19]}
	slaplacianb=${array[20]}
	chromatic=${array[21]}
	chromaticb=${array[22]}
	click=${array[23]}
	clickb=${array[24]}
	largestdegree=${array[25]}
	numedges=${array[26]}
	runGeni=${array[27]}
	runEigsolve=${array[28]}
	serial=${array[29]}
	order_min=${array[30]}
	order_max=${array[31]}

	workdir="/usr/lib/riographx"
	graphsdir="$workdir/grafos/$serial"
	sourceg6="$graphsdir/graph.g6"


	mkdir -p "$graphsdir"

	echo "$g6" > "$sourceg6"

#	echo "$rddkey  $serial"

	sage -c 'load("'"$workdir/geni.py"'");geni("'"$graphsdir/"'","'"$sourceg6"'","'"-a -b -c -d -e 3 -g"'")'

	sage -c 'load("'"$workdir/Eigenvalue.py"'");Eigenvalue("'"$graphsdir/"'","'"$sourceg6"'","'"-a -q -l -x -y -z"'")'

	java -jar "$workdir/evaluate.jar" "$graphsdir/" "$line" 

	rm -r "$graphsdir" 

done

