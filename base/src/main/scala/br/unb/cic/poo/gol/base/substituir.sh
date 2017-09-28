if [ ! $1 == "substituir.sh" ]; then
cat $1 | sed "s/br.unb.cic.poo.gol.app/br.unb.cic.poo.gol.base/" > a 
rm $1
mv a $1
chmod +x $1
fi
