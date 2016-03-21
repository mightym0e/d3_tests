select 
id_messpunkt,
(EXTRACT(epoch FROM ist_tu-soll_tu)/60)::INTEGER,
EXTRACT(DOW FROM soll_tu),
EXTRACT(HOUR FROM soll_tu)
from qdababav.puenkt 
where betriebstag between '07.12.2015' and '13.12.2015'
and id_messpunkt in (34143,36377,46357)
order by soll_tu, id_messpunkt