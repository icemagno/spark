insert into filter_out ( id_instance, id_experiment, id_activity, optifunc, gorder, workfile, caixa1, maxresults, paramid, grafo, opcode ) 
select %ID_PIP%, %ID_EXP%, %ID_ACT%, t1.optifunc, t1.gorder, t1.fileid, t1.caixa1, t1.maxresults, t1.paramid, t1.grafo, t1.opcode from 
( select eso.optifunc, eso.gorder, eso.eigsolve as fileid, eso.caixa1, eso.maxresults, eso.paramid, eso.grafo, eso.opcode from eigsolve_out eso 
where eso.id_experiment = %ID_EXP% union select so.optifunc, so.gorder, so.sagefile as fileid, so.caixa1, so.maxresults, so.paramid, so.grafo, so.opcode
 from sage_out so where so.id_experiment = %ID_EXP% ) as t1 where t1.fileid is not null  