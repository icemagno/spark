#!/bin/bash

workdir="/usr/lib/riographx"
uuid=$(uuidgen)
pdffolder="$workdir/grafos/${uuid:-10}"
cd "$workdir/"

mkdir -p "$pdffolder"

while read tline; do

	tempstring=${tline#"("}
	line=${tempstring%")"}

	
	IFS=':' read -r -a groups <<< "${line:2}"

	lineheader="gvfile,optifunc,function,evaluatedvalue,caixa1,gorder,maxresults,grafo"
	echo "$lineheader" >> "$pdffolder/pdfjobs.csv"

	for i in "${groups[@]}" 
	do

		IFS=',' read -r -a array <<< "x,$i"

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

                functionResult=${array[32]}

		graphsdir="$workdir/grafos/$serial"
		matrixfile="$graphsdir/matrix.txt"
		sourceg6="$graphsdir/graph.g6"
		dotoutput="$graphsdir/graph.dot"
		graphvizGif="$graphsdir/graph.dot.gif"
		
		mkdir -p "$graphsdir"
				
		echo "$g6" > "$sourceg6"
    
		/usr/lib/riographx/nauty24r2/showg -A -q "$sourceg6" "$matrixfile"

		java -jar "$workdir/txt2dot.jar" "$matrixfile" "$dotoutput"

		dot -Tgif -O "$dotoutput"

		echo "$index_id,$function,$functionResult,$serial,$parameter_id"

		linecontent="$graphvizGif,$function,$function,$functionResult,$caixa1,$ordem,$maxresults,$g6"
		echo "$linecontent" >> "$pdffolder/pdfjobs.csv"

	done

	 java -jar "$workdir/pdfcreator.jar"  "$pdffolder/pdfjobs.csv"  "$pdffolder/result-$ordem.pdf"

done

