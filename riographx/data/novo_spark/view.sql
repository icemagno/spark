drop view if exists select_graphs;
create or replace view select_graphs as (
select gd.*,sp.index_id as parameter_id, sp.optifunc, sp.caixa1, sp.order_max, sp.order_min, sp.adjacency, sp.laplacian, sp.slaplacian,sp.allowdiscgraphs,sp.biptonly,sp.maxresults,sp.adjacencyb,sp.laplacianb,sp.slaplacianb,sp.chromatic,sp.chromaticb,sp.click,sp.clickb,sp.largestdegree,sp.numedges from graphdatabase gd, spectral_parameters sp where  
gd.grauminimo >= cast(sp.mindegree as integer) and gd.graumaximo <= cast(sp.maxdegree as integer) and  
cast(sp.trianglefree as integer) = gd.trianglefree and cast(sp.biptonly as integer) = gd.bipartite and 
( (sp.allowdiscgraphs = '0' and gd.conexo = 1 ) or (sp.allowdiscgraphs = '1'))
and ( ( gd.ordem >= sp.order_min ) and ( gd.ordem <= sp.order_max ) )
);

-- select * from select_graphs where parameter_id = 591