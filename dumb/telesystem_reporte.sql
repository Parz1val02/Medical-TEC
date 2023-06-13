-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: telesystem
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `reporte`
--

DROP TABLE IF EXISTS `reporte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reporte` (
  `idreporte` int NOT NULL,
  `diagnostico` varchar(200) NOT NULL,
  `firma` longblob NOT NULL,
  `bitacora` varchar(200) DEFAULT NULL,
  `historialmedico_idhistorialmedico` int NOT NULL,
  `cita_idcita` int NOT NULL,
  PRIMARY KEY (`idreporte`),
  KEY `fk_reporte_historialmedico1_idx` (`historialmedico_idhistorialmedico`),
  KEY `fk_reporte_cita1_idx` (`cita_idcita`),
  CONSTRAINT `fk_reporte_cita1` FOREIGN KEY (`cita_idcita`) REFERENCES `cita` (`idcita`),
  CONSTRAINT `fk_reporte_historialmedico1` FOREIGN KEY (`historialmedico_idhistorialmedico`) REFERENCES `historialmedico` (`idhistorialmedico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reporte`
--

LOCK TABLES `reporte` WRITE;
/*!40000 ALTER TABLE `reporte` DISABLE KEYS */;
INSERT INTO `reporte` VALUES (1,'HipertensiÃ³n arterial',_binary 'ÿ\Øÿ\à\0JFIF\0\0\0d\0d\0\0ÿ\ì\0Ducky\0\0\0\0\0A\0\0ÿ\î\0&Adobe\0dÀ\0\0\0\0\n\r\0\0s\0\0\Ë\0\0\0\0.\äÿ\Û\0„\0		\n	\n\r\n\n\r\r\n	\n\r\rÿ\Â\0\0n\0¯\0ÿ\Ä\0\Ô\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0! \"1A2$4\0\0\0\0\0\0!1AQ\"aq2B‘R#¡±Á\Ñbr3ð\á ñ‚’²C$\0\0\0\0\0\0\0\0\0\0!0@\0 Pa1Q\0\0\0\0\0\0!1AQaq‘¡±Áð\Ñ\á ñÿ\Ú\0\0\0\0½qze\ê\âö\ï\ïI\ïxrv‡¨X òX¸Vtùm^§ÍžcR˜¹k¡\Îe“\ÏýžTá®W^wlñ­Cž\ÝNzÙ©ñ\ã;%MY-%¹4XzV\n\åt³°aJø\âÜ†[z4Ê±£Ó¨ù½\ÃÁŸw¨v8\Îùž±²\æ†›B+¡eÛ¥\Ì\ÝhZs\ì\Ô	‡7/¢\'\'¢\Õ&k<XKD|ðxO1VZcmeT\Ãa—¨4$U0£\ëŽGNšZ\Ù15qªÁy>¶zZ2\ìI¸‰^ôNÝ©Xö¸d0[-,k›\Ù\å±TKUXEt\Í}¢M‘\ÓPkKus3Y3rœŒ\ÌMGt\"1\ÉW7M³£M{{\Õ6}\ä\Ü\ëZW-Xh\åa@-4<–•\ì+F\ÇÞX×£1\ÌA”rC¤‹©\íñ2\ä\Ïõ\È$\ï”À	Ý•‘._<i¸5\Ý!›\îÿ\0\Üù”\Õ)¸5:? ‡LžÈŠ\Ý+	\êË·°P=ƒrY´¦Q-4\Õ\Î9©y~™[¯¥ù¨µ6Œ\èD½)\êª\Z8PŠf	ºÁM‡¡ô,T´eñžqž\Ó½\'\ç[ú/™À\è·;\àÜ–¦\í“\å36“\àž*úO\'¨–‘Ö™\È\Ð`­ƒŸq¶#\Ì’\Ôÿ\0Q\ZyW}\îñ¬´\ÇZ\åt\â\Âil¦9&µ>\Ïg}XŠ\ÊÔ•Ð©…g\Î`,+\'/´Z ±¬\Åe!×ª£\Ì”\â\ådþô\\Sû¡™ðÉ¬¬n=\ï›RIÁe¥#±\ÇQRU¸´?Od\Ê\ÇOl\äö¶\Ïož÷½œ\Ö\Óz\ËÐ†\Í\Ó\å\é\Ú\åD½<®\Zh{&¹ë’šqyXj¶¸K‰K—S\\ÎŒƒ\í³7œ=8:Q\Õ~‡\ç7ö¦s:¶\ÖGÍ…5²]‘\rfx¤‚?xœ;Õ¼™\Î\r?ÿ\Ú\0\0\ÒØ¬Uf¢\ØMWR\Zõ°-µW{Ù†½ršõZÝ¦\r«e–­š”¹ t%M¬Î–WX\Ó\Ô\ï/ò“²°¨®—\îU¯­¨qšÕœ¿\Îß¨\Ý;	õZ³O\Ø\Ôx65\îJÞ°kFSe3\ÕEb\Ç×ªÆµC¥q;\ëWó-\ØJ~MbU¯US\Öm\æ‹ö¾\Ï\ÓMj¬Ü·_üNÆªŠh´Y\Åÿ\0·nÄ«½O}ÖµN\Êûµ\ßs\Óh¶†Ô¿?¦Š\ë\ï;[·Uü\ë§kŸÑ½+«[\ê\n¢¤,\Ø+rºÿ\0f\Æ\'C™ªšp›ª5Q¾Ö™5rY[-Ö¢“·³\r\çwmjm‰±n»–\ÖÜ‰©­A}\Ø?¹hM\\\Ö+\ÕÔƒk÷±-\ÏG\Ð×¶wTTm\r\È\ÖuWw\ÏM¥_ñV5\Æl5\Úõ›_+e•\"nh†Q©r\Z©¸-\n’r‡a-v°’Ú›f¤¨Ò·w9\Õ\í-ôtôo\Ø\Û\Ö\ÚY¬@ 1\ì<·”\éF—\Ó\ÚF­‹+¬¼¶²\êº]‚-z\n\ì™\Ã\×\î[µd \ìXM\á%\ì\Ý}·©×µ\ì›\ß9¯­~\Ñ\Æ\Ç\Ô{ŽÅ·R·¢¨ºä”©µx2ª+\Û)¬$*p­‰±¸º\ìÿ\0ú›·tµc\í\Ü\ïs«mB«C¦5”\îPGöÙ—¼t?øÆ¤\Ôøº\Új\ß+u/¸X‡;(8²Š\È\â7T²Ê¯Ù·p}e£Gb…«fÁ}z¿7RšùJ‚\ígI\Êõ•\Ó}Å¨D°\é­\Ö\ËY‹\Ü\Í)\Ó\îdÑ®‡ú›\×k¿\ÎÛ¶Ý†¦·ƒV…!Qbnj\Ø\ßGYú\îú—Z\éõl#cz\Û\æ¢zM›;\æ6\åÒ­\ÆsW\í\î¿ùŠ«P\ÌJ>åº²Ï«\ÙjÜ¯K\ç&—Õ±µ5>nõ—Ï¹þ;n\ÖÅŸ\êV4µ\0\Ø|,\Û`à´µU\ÚÛµW+ºµ½¯„úu\×Q\ØK\âk¯Ö–¨\è|ÖŸ7kof\ÆuY³½_4úVs\ï;)]\ÊO›~=-v\ß\Ìúe»Kj\Í\Æe}\Ûl7\Ýkµ—©,x	rÀ$;\ËUWlò\r¾\ëzú\Ç.P0E[¢n=N¿K¦\Í?´%¿q«û³\ÇP\Ëö+:\ßIAxº\Óÿ\06\æ}]D¥jB†Â¢J\ØHûhc¶\Ãmf*–Œù<³—5¥u\Íj\Ó`J³\ä§>\çK/¾ªFÆ«}¤z\rv{S©§\ÙM\èrZ=™¯bt«\å½ö\êü\í]e³R«O¬`…\Êõ\Å\Æ}\ÍN\î\å\Ïµý~¯²´\r¯ÿ\0Kï¦¯ö\ëÈ³Y}Ø·(l…ý¦¦&¹yH\Å_\éÿ\Ú\0\0¨ú5ƒ8•“s\n\"q1\ìˆI†µŽ˜óZrŸ×q+ƒ\ä\"\àx$@b.!EÊ …:Œa\éP˜´À L\ÆÉ˜\Ç\Ä4\ä\Þ?\Äþbú²\rfc„[=Ø¦*´°Ê¬õ?™\×q8‰±\ë\Ç	\Ô\Óœ&3=Aü•9i\ÅDkg\îb¤ýRvû÷9N\é\Ø%\ç\'0}³—Œ\Î>õœAœb&%€™†¯!ÇŒnR¬\æ\Æ\Ã+\Â<ffÇs¤™]Ž…aˆX\Îr\Ã\ë›	[`\Ï\ëÅ¢\\€LN3T˜«#Y\Ì6Al\å\Åýaµa²v\à‚\'y–…ž¦!A19	ü\Ì4\åˆ\\Æ°Ÿ	\Æ2OqP˜Pd˜@ð\',Á™\ÊgÁV&&e´\æ\n@p$Ì®\Ã;!°\Î\È=\Ïp&g\n³¬øÇ“\0ðs˜\í\â¡\ì¤\áˆ\â	\Ë [€`¼\íi[ff[v\n\ì{\ç\Êø=9Rq˜€F\\<®f\'TV\Ä$\ÌxV\"+b-\ÐXÏƒ\àúœ xù0\Ì@!H+€C\0˜Ÿ\Ä\Îek\Ê‰\Ê33\äB³ƒf!b\Ð~üÕœølKepC=þ6yÿ\Ú\0\0µ}‹&Aœ\'aýg\ìcdD®8\ZWg(f%ˆ-†¯˜|q„d\â3‰„F0;Fc€\ÄE¢)ölao±b¬~9À˜üŠ\à‹ÿ\0|j½V\ÂYPŽ˜\Ç]ùNm)÷\á33\å„a@fg60U?A\ÏÙ§\\Às¢uZñ\É\ç9\Ì~…Z~\Â\"rŒùˆDÊ˜‰-˜¸–bV=c\Æ\'\Çðh!·\ï\ÌF˜n\"pˆ=ñX\ë‰Ûˆ.†\é[y\å3	\Äc3,l\Ê\é\Ì^{0V!ªqÁ\ä!ý§Ys‡¾3Œ¬ûò¬fg	À^‚`O-\Ê+\ÏP°—€\Æ˜}@±›9Žb6`c9ŸJ\í\Ä7NpY	Œ‚u\Î\Z\á€}ø8˜Â¼r{—×…X#N\Ñ\r™ƒßŽ\Í98… ˜–.&%uCD\á\Æü\ÜN\è\Ï33Š\Ó>xø\Ì)˜y*jò‰bñð!ñ‰ˆ#7¥i™\Ê™9üœ\â1\Ì\n3\Æqø\ær\Ì3ø™ü\ì\Æ<.eF]ü·“øW\çÿ\Ú\0?d²4®÷\éHu	]³\ê­«?\\D1\à_œ r\ç÷2\ãø\×ÿ\Ú\0?„[:Z\rJ|B\Í\ç\ä\áÁ‡N+õÂžpTÁÏ±L.¢ÿ\Ú\0?\é\ä,‡ðWFLÁˆ\Ýtò\Â÷\Z…\Éwj¸\ÜSNªY±ÿ\0!ü 3q\\\ã\é\ìTús²Sñ=B†XF90ù¥P\Ês”Ä„\áeŽ\Å[\Û#W#z\Ê\ÌP¬a\æ—iFP›F^1º\â)\Za“IQ—ŒË”BJš!‡‘\Ð\rT/ô\ÙSÁE,þ¦\ÂzL\ëT#?M–„%O´#)\æ–9@SaCð]XK›ô¦q,’ù“–\0Š\Å<²ž\äø³{•Ó‹•h\È!•S0o•M\"÷Å®+1¦[$´v_PF=\×â¾”[·tÛ«±\Æù|®\Ëÿ\04`=-•<\Ò>õ\Õõ“–l£\Ã	~;(zœørk\Òò?hWt1c\Ë>S’\0\r\Ç-„þ\nJ\Ò\r\Ò\\\Ù.ˆÝª”£Ä±qUñ\Æ\Ù\Z\Ë>\Ê/£ŽVqC\Ö\å\ÒýŠ„7_6io²l†‰ñ‹ñÏ†¡ôFgš\ÊKÞ¡‡08\ïð.þ<‹ª+¨÷“\ä\Ð\äù-„˜\ã_‰R\Ë<É–u”\ç\Ìkß¢\é`Œp¾¬Ž;\å›!ùhß’–YNQ\ÊKý¨\0f\ÜU“\Æ.¯\Ô\Æ)5\ÕNC•ó\ç?©	G\Ó\ÄDQŽ¨‘f5’\ê\â\ÈÏ·jbnd\Ù=\Â&Y\åsô\Óø#\Ä\ÑdÇ–@õ#iVˆ_(\ë9UHk¡_L‹N¯²\â²48Í²·±_pe,¢˜\Ä\È\ÂxG÷¡\Ë:þ%^§$²JB»7r7z†.[—m·G©žm ´\Ñý\Ë\"y¼ó|J#<:ð\Øùƒ+\ïlZhž«K)§eK\Ô}<°¡£§Á‘þÚ«£+2\ï¹§qÙ”e%\È\ì*¶OØŸÃ“w£¡<œù4\rV^jk¨	ù„_™\Ô\è¥\Ì{P\Ìe\é…!h\È1k@²ü8®¹,bje|ü3ð\É\ÆNJO\Ü\"T­©£Dùˆ\Z—¹D\á\Â4zÔ«\'\n¿\É4\á\Þ\æ(J#˜P.h‰6Žš0Œ\é’]kgÊ†¼Löd\Þ>Ðž§™[w¸£vdÏ†DfÀ\r±\Úc_ŠiÝµVÄ”]©qóqgmU€›%¯qW\Ä{C\'!røx•djP«\Ëh¦ÿ\0t»Tb#,¹¥¦~.óÁ\Û\Òús\â†2ó÷\ËòF\Ï|±<\Ö9\á‰‰“\ëð_Wf8¦\Ã!š\Ä N;‚™Iò~	ú3««b¹tüúU,\âúP&\Ç+\É7_8Ä‘!£)fœñ\Ë­¹}_ƒ#†P\Ð}Ë›_Šð| 2¬›³r },Ž<Ze2¡?µú(\ã\Ç\â™\î\Z\'ˆ”óŸçœœsv“RŒ3\0q!Wˆ\Ý7¸H\êªT~yn¹–=Y7?f©ª\îŒLªcElžxü’E¤c=u¢\é\èv;)K(~Â¥ma–=3±\ãƒ\Ç¼y[\ì\nË¥<D9½sN1\Åû“€²¡)\0*Yý4.\Ê+(\re\ÝÚ˜¾(ƒX\ï\ï~\nŸ, /˜Ž¤šFtÝ‰\Ö#e^A\îL ÛŠ®\"\ê\êbù 1b½µ\É4Û³Ç½Z,\âvFB.SA¥½¨\èÂ¡õBo\à/Da8¼8öö)˜\Ì\Ì\Ëy{s\Ç|²\ê8²zyBQ˜”ó\Ë`6{%\ê½¬\ë’©\âu2a\Én²µ‹7r†K\ç<o\ÌF\Ý\á\Ä!–\Î?P˜š\ÑD\äõ1“V]\íÿ\0*)ldˆ\ÆU¸\ÇP;GjŒÿ\0\ëõ\ÞQ\ç\rØŽ)Ÿ‹¦\Ål­ñ\áz\ÉÚªB*eT!š10¢C‚º\0\Ú+(¾\Ê}k!\ãˆß½s;Õ¸¥{ki§\Å¶\ÎtœHöþ+¦dAþ	\æ\Ã#„ä¬€¨~*1\Ã18LsJF\Ð\È\ä\êc¤£k!+¤%\èu\ìX²]õ1·q£ý\È\Î\æŒ\åy€ \îQ‘\×t\áJ‘ì¢ˆ\ÄÁ¼H\É\éÁd\æ–\Ñ\Ø %-hK\Ód\éc5•º’Ÿ!2ýÄŸ½§³–DG±\\!\Ôý\Ú#WO—\'æ¾”o‰\Ðè¡ŽZÊŽ4NŒƒƒB|5`]Î¤Kµ<Xð\ÙPu\Â<P€žô?\ÑG	˜\ç`j\Û\\?IR2G/½Ñ´\Üv«‚ñTlSih#JˆœTH\â÷\é\ì¢\îL™\n]\Å÷WpŒG‡Jzxü\Ë\éÍ¤ºr¨X\å:c\Ë\Ê\Ç\Ë/\îó£\Õz_’p„[\åtrÊ¼Nê€ˆŠ¸Jÿ\0#\Û÷Š¡tmœk\ë_wtÒ¤\ZÜ†vð\rs£<N!Z	x¤6X\Å<Æ¥Br\ØËw³U\Ìk\ì\åöG¦+²k¿³ÿ\0¥ºN5\â‰\êH–¬H•8&`\ä\Ýú·	ý>Bû\Ä\È3H‘\ÌtoÁr\Ï-û\Ú$š\r\Í\"\"\íö)t\Í\ÜP:R¡WFúb\Ë(;d:\å\Î\ï\Ç\Ùÿ\Ú\0?!¤]\Ùï™¸g\Ù\ÒZl€UI¶¿ˆ\ÛV•oˆ©‘¾!x v)\Þ\ÕAih»^¨ê»€\Ì@•Š\Óo‰Aº¯$•\ÙüÅµ\ÅweÚ”ð\Åò$©]úõ0›.p¡QÐ†¤E\Zpl\àðKC-!xt\ëõ)t@x&¨«iw\êä£šÚšq2{1\ê!vP­>l™‚\ê¦*ŒŽy‡õ,xœT¨T9k\Zj\Ë v¤ª¯v\Z\ÊvÁÿ\0!ƒ‰gƒó=\é_QE\"\ë_\ÍAJ¾ùkœ\â¬co\ï¸ÀjQ§øKRy5\ãÓŸ}F\è=ÀyÌ§L?ú_õ/v°¬\áåœ3S>I¬8¼\Û-¤W¹8¼u\à#\\\Ôy·Oqk,S\èð\×\ã¹VõQL\rY<\Ï=øûE5t«j©ñj›\\1?ˆVˆ\ÒsòŒ)N2±\ìS\îQž0Wx®\ã…c+ýÍ¬n˜\æ\è]ô\"üL÷\È\ì¨3ÚŒO\ß?P*©°úþ\ØJBç·‚\ë\"¼F\Ü[§D2:R\è=²¹\Æ^>\ßvÁ~\í¡{\Åø	f0\ìm«*zƒ\Åf…Yü³\r´X>¡\Ö\0‡Ó¯2©Y­s¹›Y i®N\ã\ÅÂ²\ê*\æ\âk+—ž¦_ =ÿ\00\"\æ®ó˜A1Ê¥iq/®a\Ó<\Çk­¾cU\Z3\ç\È\äz\Ü%Œ\àz˜»d&oXú˜\ël§TÄ”||ŠÜ­]\ã)M›Vò„\Èz)¿\ãª\á)¬­ú%úÒ°	\ãó˜î}òòw>…	ƒ\Ò85RƒÁoõ“\'\ÜM\ÊóŒ[7\Ð`‡\Ï³Ì m\'L_\ÜMŽóOng\ÖEòx˜\0]8Jð1\Ë\nŠ…+ç¸n\ÅSþÃ \âòs\Ê\ÌJ+™Bôúaª2\Ë\\p^å”ƒV\Óõd!P`\à‰\ÚvƒZ\áG\Ä3\ì\Ô\Ë\n}B}Y“u\Ä\ruõS`n\ÔuRØª(WF\0¼FpsúL¥\ì,û‹ñ¨Aöpµ§~’\ÂÀ\Ý\ãÀ\ëU†1\Õû†”\Ö\Åu\Äqr†>ó,®B1[rù™–\n\å;%vkKŠUŽ\Æ\ë\Äs\î·ý\Ê\Ý^JH\ç=Zª4½‹†]\è\ëyq/\àMp\ã\Ãÿ\0yŠ\Õtf½@S±}ž S“B\Ú}JŒ’=u-Oõw¸<ú\íc´\Õ\Û_\ë€{:\Ôdv\Ýp\å\ÑQ\Ò`Š€%¼\á«\Ë¨•qW\éž\ê2~eP\Õ#^¦\Öes\í\ï2\ä\Å\í¯-C\Ì‚µ\î$	\é\Ð%wae\Øy—½b\Ú.4¡\Çd\ÒGP¬¥X‹U«£R\êV,xÎ¡µ%\\i\Ð”o‚ý_û\ä;\ßõÁ\×n€”b´ŽS›˜D©´ù–\Ç\Øöœ&Xt~¦\æ\Üþ|Á\\Æ´8ú‹n›u\nð\ìf¯¢¯óR\Ä>P\ÑvŸ\â\ã\ã¡f6°\Ô,»„8[%­¡³\'ˆ°S†Kæ¡—^H\Û\Üz­û¶\Ë+p§G”`\'”+5\è~\ÙBsD8\\\Õ\Ç\Zm¶¶s\î^\à¹@0BËª\ß]ýFÖ«ƒ#ñþò¿\Ðó8´\ä\á\Çp\á\îd}\'q>³«nõ-Ê”gdNtlX÷Qÿ\0´qfº\ï_[Bˆ’ž\ß~#\ÎÜ·†\È2r¹¡\î ¸H…¶]Ž8`—Ø·¦q¹e”Z~:†¸ô\Æs3¡lXA§È\ÑO€\è÷/\ãƒ vEKŸ1ºIù\Zø\Z†6¦eäš¿D¹ei­u©’\ÇhYke\ß\ÔB\í«ô¢q6dô{ºX€ûkY:õ˜r,\n- \Ðum„\ÃG2\Ïy%uG^Ÿ™F\Ö/wi2„\Z0ü3XFù‘\í”\rÀW\àA­\à\Ãu\\\âxFZ[)E\Ý\ë\Ì\Åò¨\áA[d=S÷)TW4q÷r¡p\ÍSœ5\âª\×\\?øÁ$Œ³º:¾c¥¢ø!k~ :X\Üz\Æ š‚#V:k]Dƒdr|\Z0wU°À\á\Ë¬ó\íõ\ìýÊªEiÑ¾\çˆÅº¸°\Â`ò\ê[Es÷\Î>>µ,j\×,c+uŽ£¼\ï?È¡‘\ëÜ§/„ü\Ë%¯I>¸†¦T•€^qÌ«b™Cg^G|ÊŠ“%‹þ\æñ1Y÷’j´BÀ5ZG	1§T\ØA/¥q^œ/\Ù\äz\Í\å?\éž\"óÕ–¦ø\à|\Z^\"ÀPGw”2:•-¨µe©Ÿ©p¡C›\'Æ†\Êz\ê1pÐ¶¥©„´„°½>\ÌlF(»Ž\ã.)ˆ\\óú !\á¾\Ø\×ÔªÁ¬\æv\íû€ sXLf\à«\Ù÷,\Ó\ÔñøG\'Ç˜(9—ùNFý\î]\Él`úq?¬\Ø\Ø%SY•—±\0\".¨Švl\Ñ9÷\ÜBþw÷û‡\Þ`Ä­\Ù¤;m\äùrËp}©¾.ŒB\ÚE9•y˜„+\È=Cìƒ¸õ,­\í†þ\à<ß¹\ïyr3–{ry3\Äô)ö˜™?pr\ÅVn\âÑ¼@,\ì\á:©@Uh\×-ƒœc3\ßöUV\ÕJ\Õ\Îp\ç´Á\Úp9ýb\\IUSô\é\â¸ƒ6™½k\ÌmVT¦¹ó\Ø_\Ô\ì7;\Æ£‹~.õý!rÂŒ¼\Â\í?ÿ\Ú\0?!¬\ÖJ¶\àüŒ¬g.\rQOœFm\ï\à>3–ñ\0Q	Y™[\Âa/\æ.Ir»\ç¤\Â<±>\Ñ\Ù0Œ\ÌjYÌ®cQ\ÖÀ@”AŒ\Í\ÊQ«ƒ00\ÓÛ—r\ÈjH³\n2þ pŒ]½j}µ)¢{™õžœ\íü4\Ä\0òb¯Ì³\á(GD))”FF3¼3±ð‡\Ã+w)0[ƒ\Ìi­Mg”\Å6óˆ[™\ë™x•Zœ’W	C¦$\æ\æ\Ó\Äò£ß¦X[–%\Ü=\Õ þ\å­\Îa•Ì³\æo‰œh‰ g¸\r\Ì^®\Ù\ëOöQ1X*«›X+Ä«r\àC‘)¾b\î?ÑŒS,\â#XR9¦Y¥\Äp˜Q¼\ÄyDHŠùœR–Q•xCø”\"\\\Âõ,yw-ü¨esÖ§\Þ%ITA˜™\îdy:\"ˆÀÁ`x€%%›\ÚyÈž?\Î\à\âmw\èOû—GŒl\âP \ÉQ\Ü…\Ù\Ü\Ü+óz‹idpj\rT¨Ÿ9F¬L&¦‘¢5•‡\âVõ\n¯„ =sðü»%#¬-?\ÆHgqi°Q2ªh£¨7qŒKÊ¢Œü|	n¾KcDƒ\Çó* ù\nB×ˆ\nÐ€\Ñˆ+/L¿H_$fœ|2@\Ã•\Æ3,\Ün[|\ÌN\Î,À\Þ%«:˜\Ýf)_ù?q	ÿ\Ú\0?!L\È\Æ_…r1\Ûq*¡\Ã)f˜\æ79…ÀÁ\Ã\ÇÁt‹Z™¶\Ël™¤Rñ\åþ1b·\0\Ç¾ °†¬J\"¬\Õ\Ê8—KcñÜ¸b5÷ñ†@‹\r\Ë?óxnÏ‡•\Ô„£X$­ú¹~gLCz<š‰Í˜\Ý\ÞÀ&þ!™Ró\Ì\r\\‘\Ñ\'t}¬\Î!©|´G–nm,L“\ÇD(`¢?…©”Ú‹L‘NÈ·Šš\Ä)™\àB\ì%š0Jú˜|\Æ\ß\ãqÂ£rŽ&–\Â_QQ)\Ô1\Úü=¨`\'\Ä\r¥Æ¡iX=T?hé˜‘\Z†pbò\áHxòpC\åcE$· Š—ECÒ€e\Ûñ(\Ê\Ê b\ÔX.fR\È~1œAž\å\ÓR³+õ\ÞþA¥\ÍÁ0Lñn5zˆ—ˆ-Eö\ÅX%£SU\ÔvÜµ©Šmjp÷@\å˜\Î\Ìd®°\0ÀŒÀ‰\ß\05ò€\áp­-|R<\Í\Ç\Ü0ðM(\ÎQ,—-2ü¥.an\n;`˜\é˜u\æ6CoüQª¸Ž±ø\Ö\î^|\á+¢H*\Ø„›É€ŒB\\\Ïqÿ\0†¡þ6,1{üÜ¿”;}\ÎKsi(lŽ\Ïa|jkPGÀ½4£ÿ\0S\ã‘üf\í1d”öšgp…|18„\çŽ\çÿ\Ú\0\0\0\0}\ÈÏ“jµ5\çô#|\ÔX\ÈP}ÿ\0\×>qÉŠ>^‘¬ =8VÒ–\É$õüô\Æ+4˜¹@\ÓÔ¼/q\íšgù¢¿Àr\Ê4´ž×‚\èäª€Wž=óñ\×Ì´/P%Á‚\ÂõR\Û!\Ú\\\ÌH§J‚=+\Ä\0«RË˜ÿ\Ú\0?&#W¢ð(”xe1 Q¿¬\ßR‚`:PFüÜ¿k\Êm]©ý\"2¦´aB\Ï\Z\è”‹Ž•d9\Ç(†q@-Z9ŒA9¬\Ó\Î:™ß¨£¡9¦ó„J\ç¬Á·Y¶û0šŒ\Ò\ì$TkM«­\Ëp, €G\É/¾\à¡nL66\\`>\ã\Ê:\å)¿ú›\rk-Z\åc$ZZ¡\Ã!ˆWF‚Ð¢4h\ßs™\æ\Ïybþ ó\æ´W\Ôr¶\Û\ì&À 	\Å\\\\	\Òñ*d\Ö@\àT¾–W;Õ‹»\Æ>·(*µ††\Úq\Ó^8/\â¦q©*ñkˆ¢µÈ¾‹5}LƒñDc‘N^¡\é1hs8«	\Ã‡\n\Ú\Ñù†hd”KM-*™òX)Ix~\æôJŒ—€4xü \ÌjX®	I@¹S\Ìd:PKB˜a\Â`Y\Â/‰G=\â\år7)XÒŽ Wf;46\à\Â\á8\Ø\\”?¨O.“y\Ùx?¨Pe\"ÑxX\î]˜xœ&ƒ/yœznøË•|s¸·L\0¢\ÓD­¢\n\Õvh•˜~ Z]°mz^ÿ\01\0lX\0\æÃ’ü\\(  \Z »÷ú‡r\Ð	Dw\é€•-ô\ÖjoF˜rØ¬„Í¶K{Eª «ZLz/†e\É\Z¦JŒ«\Ù\ÜX+\ÑqZ\Ñ[®\à\',-\'o\ÐÇ¶`\Ð\Õ\Ñ;\íqZ™ªk\Å]4\â·k\n0;l,7±AZ\" ½ôurÚ—\'döÀ{n(4(\"kW\"óZ„yL®,#/Œ\ËYVKDrš\n/\Ì\ZeT6M~p›ÀEÀ›@3\ÒÁ‡ \í\áp‹\åj Y\Ý%ý\Â\r\Ël\çeõ(…eö¬X`\äC~C;*‹B…®/nòJ½C\éñ®2=p¬\ãS\Â\é\ìƒQ\Î0¨6¬\æõ¹sØŒ\n\0µCxù\È\ÎÆ›}DpTQþd\é¨ú\nr*2y\áÁr˜ó¯0Eºö°«n\Þ9—a\Øo4\ØVªaöt<!X¾&c	a\Üxµ{Xf\îY™\Ò2(–Z2>¼\Ëñ\Þ@\Ü{jZ†)(\Õ\á.\è\ÂCMŠ¡…òG¡fä«ºÍ™b[m2˜RÛŽf_§Á\0¨‡w™I\í\ì@R\ë\n/¹I%fJÂ¦±²%x‚\Ú5i¾eˆD¬³ƒ\ÌQ*7UOÑŠ5MÁO.•\ÔShÀ\ã³÷\áa\Õ\0óU\nC\ÝÆª9-\Û]\É\Ã~Xyœ\åŠ*iVã›LLh€Š:\ÔG2·D“òh-M^¥º\Å\r@\0\Ûžv}\ÇÀk\ë¨O¸R\â\Ò¤)\án²ÃŠß˜	^¨S^Cœ˜fÙ”W\n²¾€\ì\Ü\ÄTJ\Þ\åüLŠ]p\è¬\ãzmV\Õ\×P—©A«¦\Õ\ÅK€H…s²®M*©`\ë›™FdÑ]h%õEG­YŸ\ÙjU/M5R\Ê	C¡rº\È\\³\ØJ\ìy‰Ÿ\é’Ayy*ð•‘É— qªòz•\æ-I\Íû¼Bû#›\áÓµŒb’±J5MƒMf\r\ÝQ¶\'\í/H+T\á\á5\ÙloRŠ\Úƒ#9¯f\é\n\"–ðW\æ!ejsuô¨Í¼ E$\àC²ÿ\0ð²ij\nQ\áTx¼Ì¼ƒÁ†\n»¡½³V™T\Ðû‘	lßW\'\Ä	˜´ªzq\Ä\Í> .;VÓˆ\\­¹cU»jYø…\çŸ d¸Q H¯-Ò¼DŒŽƒø‚å‹—‚>%ñe(loÅ°\îy»·½Wp¤©J­h€Ì¦&Ð“M²8¸˜gF\Í`\Äi3«H\ß`_\Ä\\H½\Ûh£\í–@b²›Çª™<\Ønð`/À&\0‹&\Ãü2O\"\nŠrúJ\Ðb›€£š\Æ\×:Jgµ\Ã(•M\Ñ\ÖÑ§e’°€ª(5N÷¸\ÚŠ§Ü³\0ŸmœFsx\Ñal/.Y`\è³ªÌ±¶\å¥\ì\Û1…Á@[J-â¡‰£Nöø.g^Œ”‰œ^¦”17e\ë1»õBÀÊ¶~({\Ü\×/¸¥¶c4\Õ\ÌÞ¿\Ö\0¥5_q‡`û)Ê€Ñ€Sù·@\Úw\åL\Ê\Îú\"¹£„{“ó8¨\Ë6–‡\á\ï‘L`SSa¾ñŠ\n€¶x\í¼e_X3&‘+‡`&k!²¸½@(\×N«‘³¾q\í‚h$%önˆ5]\0\Î\Óñn\Ø&\Z¦z™)ÁA7ü¸¯ˆµf\\@\ÄÀA\Ö;´2ñu+:A\ÍNs-V\Êc\ì¼J^µ]F–RŽR Hp3\ÈA7z)J2\\«\nn2\Â\â\Æ1i±-–o\ë9•\âr%š­«ñn\êQ\Ú\Ñ\Ü\r1\0%\År›®m²ßˆ`¨Ó€z\á»\Ëe\Ï1\ÖÁw\ä´\"a³9½Z=„Uh¼Pq*\è(K(¦ðVGÂ€–1U[—\ÌuKu\ÍSòE\ÊU¬L\çn\æ2j\\™TK	‘\Çp\\*¦ø3#´\n²\á\à\'I\Ô\Í6¡\ê=QbdŠ\r5¦gZl\Ìd 8\ê1ÅF2\r=T\È\ãÁmmK1¸@„÷\n\Ù`\Ð\ìó\n¡·,\ÊÈ„.\ÙfñÀŠ&\Å;`®*\Ò\n¡ˆb\Zô_LoR\åR¥/k5\ä­K=F \â*rnf°\ÅnA·1„\ÓOkOQ’œ7,Nÿ\0¹\Ñ0mJd¾l7–™Y2\Û\r\Zò„¶\ïlÁ\ã“\Ð\ìQ/•ù¬\Í\ÄJ\n;‡<\n\r3…‹s‰‚\Å!j\ï/”C°\n\Û½\Õ|\Å†Ì–D†\Ô\ß|\æ\ê-ˆe\ížS\é\â	*f\Ùl\0ª†@4Ì“@*ÿ\0¸h n™\Íd›\Ý\nO-QnbŽKX­‰¸V\Â5„[pU[\Ë\\X22pÅ\Ü[WÕ¦]Eôü \n\è8žŒ—4•œ\áI˜/6y‰p]0\Z¨\Z;\r@\ìF8–\çhl\nEGk!\ÕK0]@DUyi–\r\\\ÛkNt eÀ˜•j(\Ø\ã1\á\Ì\Æ€P\"\Å;9‹ªÀ!›ÛŠ\ãý¡\Í%µA5x›“t>e\è:¹lWù†J)\ïó\ê¨ì²•-\Ö\0\æ\à}€(úG¨ˆ3z}D‰\\-q\Èý@ZŒ²´\à0©G\Â\0UO,¸Ä´¬AB\Ê4\Æ >6¥R\è\í0N”k~…†.«c\Ö\Â€§\ëlŽ7Ø¿¼%µÏ¸\â\Ï_€¶}J\á/³Q0¡vW#¨CgE± \â°Dw	¡G•hA¼\0V\0\Ãc¼7\Êh\ç\Åò\Ü0+\å\Õ+\'bS–h\Z“\Ùüu\Zl¬X\í\ÄÉ¾xÞ \î$­;÷9<\à…¹\ÈbØ¬«ø?r‡Af›3Šÿ\0‘u ƒºƒCŒl\Îi\Êoÿ\0p\Ã±C!ê¿¨%óv\Úü#jpË™F‹÷&ºYKIX\åÀ°F·.\Ï\Åb.PµR\Ò\î†ü¯p[±Šy˜\ÖÒ‚\Ü\Ç\é“\Ä,œ•\ÚÅ¢bò[opº@jTšQÀ½L€\n’\ZZ-\ÙÆ¥X\Î%iYbnºs?ÿ\Ú\0?\à\ËµP´B\Ý;7/\í,£ÏS^\ë½¢5…Boò\Æ:4Cq-½0mWŒF³X€±j/q‚\ÝC@bR\è\à•¸ºˆ\Ú\ëF\Æ79‹Te\î9m\ÝFŠúŸ¢\Òø 8\0\ê.°¿s$~\å\È\ÆSô„\Ö1M 4—u¸¢šˆDlw\Ì\0—÷œÚ3Ec¸u\Ì>\èòÙ¸jð\Ê,N\ìs-«ˆ\ê—ú%>\ÈIŽ†-˜\ç=…¸\Õ\Æ4¬0%7\Û¢úQC¸Žbj%`n%pik—\ê\Ï\ÄXB(*¡)ˆ7YZ¡‰’\Ó\ÌP%50\ÔÊ‚„…È­“5Á¸\å\ÞfÁ’c›y„\í`X\á\Ë\n¹¾m¶‰\\aXc\É3\Õ\Ç‘ŠŽ\É]Ð˜­@\Ï\É\Å\Ä\áõ¸\Êal\ÜpX‰)=â­…¸aw*K˜GYrB\ê° \ìjZ¤•I‚1fÒ±§©bÀEi\Äþ®9Ïžƒo™—½ü )4–†r¸çµ¿¨«\à5‰QÁDM\ÃE»œb\ÖG1FwþbW¹¥r¨¨(Z[zŠ\n\Ï\Ë\Ë\ÊPŒ0j\Þ&Ò¡L0¯¸\È]ó:Y–> b£ô\å\ïŽ\åG†VŽr\à\Í\à\êd 9\Ê(•­KQ‚\è†æ°Ô«Ú‚8¦\×eKÔ©¨—S„ŠX.h.’Wb\åG&gökñ´\Ì\Ì5\ç\Ü“¹D\Å#c\Â\ãVy`«y\ê\'`òŒ‚8¯1-X­“vðbu\â^»—P¨S­Ç‚¾\æ%\ÅX‚F:P£-\ÅD_õ\äù•Í©ž%L/\Âf§[‰TZ+irø$„m‚’\åY+–ŠrŠ‚©©\â\Ëp0\ÑJ\ÕUkú•(ÿ\0\ä5\rABdmœ\î/Mi÷!©¥±±s¤ØŸQ\ê.b%®¢G$ª\É­À\Ùÿ\0´±ºš\ÙX¾\à¼\\iK`Š5¾aò\à\åtG\"\Â7ô\Ü(\Ã%\'¹A¶	rÀý%\0D\Ë\Ó)\Þði‡\èa9ŠP__\î\0«	—,nKU0\è’Õ¹\æXŒ¢\Å_Ž\"`rKB¶¡NúK\ÔTw…\Ö~Ë\î^\Îw´†*ýw.|V¼\Ë|Qõ\ï\Þ&beqz‚hÄµ3ÿ\Ú\0?O&‘0	˜\Ã\0ô—§§ý6\Æk\0nT¿\0`\ZŠ²Žj^*…Í¦Q,™e/L¨ƒs#…\Å\Òx\ã‚`Š\ã‰¬+\ä‹ò‡–ˆz€\Ñx\Õ\Êû”Ï¾_\n&ñ.\à,¿\Ä\Äx\ânc\Ô\Ù\åª\å\á15†3+=q(2\"‹1\Å—ø·þ\à8™ÁŒ«’P\ÈÜ¾±\ÄÁY†¹üˆ÷‘›Ÿ%R\ç\0©Ÿ\ÛA\â‰pB\êú\â¢\Ì\ß?\î%5\ÍW\êQy3Wp\ËQ,§Ö±H‰z\\\nZRU0.\ÜiA\è—\"-r­¾3Ë©™\ÊwB‚s,­C¤oqþ7Q\Z‹\'IZ…\Z•8…Ì™~ 2Xð\Æ%FH‰e\Ã\Ñ;R$K‡dAS—\ÜGðŒW‚‰2¢	¸<2¿t;¥.ñÑ’U0‰›l@±š¥…R\Ñr\à7A\æ`€T­ûB8ðL\ï\áCž!eúJwhÈ«’\Õ+(©\í,lˆ\Üx\Ìƒ\Îb]‚œQß÷sÁ~\á\×ýƒ\ã¬\Ç\â5(|ð~Š|d…)Á\ã6ƒ\àCO\'ù\âx©„\îb¨#>¦|EñùA\r-ÿ\0™”µÁW¿ð\Ë\ì\01:š?´U-KºÄ«ð„m\â_·g.£$Ì±d`°}3*\Â\ZT{“küKq\Þ`\\ªI6Š\ÊAv©C\åô˜‡\Ü;”{!. \ÜE«Á.N®˜¤6ñ\Ì-\\kó\âZe:˜Y\\>…JŒcTE\Â\È\Z¾þ(sFR·/3L¦ \ÅGHl7¨\Ì¹™¸•d\Å\"\ró\Ñ\Z\Òò\Ê(\Ç\ì²\Ô¢®\âÁsb¢/ƒ\ád!L\ÌbKa\ê\nl_ÀK\Ç=\Ã4JO2¾\Ý@p+R\Èn6¢—øCÁ–KP6S0\\W_Œ³ñ1˜A\æ%\Ü!Œ°\Â2:‹ÿ\0€bY‰Jv¸š˜a\ÒR2\É\ìÿ\0“$¨ #LµÀ*T]\â0\Ë‹eš	¾¾|\î\ÂøÍ•‹ü\ÌBY÷Y,¹ô€F¿\Ïñ)\í±\áR\×MÔ³r‘ÿ\Ù\0','Fecha de la cita: 20 de abril de 2023\nHora de la cita: 10:00 a.m.\nMotivo de la consulta: Dolor abdominal y nÃ¡useas',1,1);
/*!40000 ALTER TABLE `reporte` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-21 22:14:22
