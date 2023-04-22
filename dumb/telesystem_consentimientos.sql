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
-- Table structure for table `consentimientos`
--

DROP TABLE IF EXISTS `consentimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consentimientos` (
  `idconsentimientos` int NOT NULL,
  `consentimiento` longblob NOT NULL,
  `historialmedico_idhistorialmedico` int NOT NULL,
  PRIMARY KEY (`idconsentimientos`),
  KEY `fk_consentimientos_historialmedico_idx` (`historialmedico_idhistorialmedico`),
  CONSTRAINT `fk_consentimientos_historialmedico` FOREIGN KEY (`historialmedico_idhistorialmedico`) REFERENCES `historialmedico` (`idhistorialmedico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consentimientos`
--

LOCK TABLES `consentimientos` WRITE;
/*!40000 ALTER TABLE `consentimientos` DISABLE KEYS */;
INSERT INTO `consentimientos` VALUES (1,_binary 'ÿ\Øÿ\à\0JFIF\0\0\0d\0d\0\0ÿ\ì\0Ducky\0\0\0\0\0P\0\0ÿ\î\0&Adobe\0dÀ\0\0\0\0\n\r\0\0\nc\0\0\è\0\0ô\0\0\'¤ÿ\Û\0„\0		\n\n				\r	\rÿ\Â\0\0n\0¯\0ÿ\Ä\0\è\0\0\0\0\0\0\0\0\0\0\0\0\0	\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0 !1\"02#A3%$4\0\0\0\0\0\0\0!1AQq\"2a‘¡BRb# Ár30ð±á‚’²4\Ññ¢\Â\ÒC“$\0\0\0\0\0\0\0\0\0\0\0\00! @1P\"A2a\0\0\0\0\0\0\0!1AQaq‘¡± ðÁ\Ñ\á0ñÿ\Ú\0\0\0\0\ì>f\Þf\Î\r\ÛO\ÔvÆž¯£\ÜM¯/\Ò·\ÌÖ\éHº\ÛS—J\Äj!xõS\Þr€¥h2ov\á$IFaô´¶õœ‘5h\\8u_«©Èœ\ßk&~\Ñ\Ý\â\Úò\älJ8ý‡.\áj\ÕBy«…\ä\ÊÇ²\Ü	7]«Tµ\Ñe\Æ\É2OneL“$\É<“Û•-6F\Ý>JoHqôUü³Ò¬UX›0‹¾ˆú;\ì9n²L“$\É2L“$Ž-õ½l–\éX\Ñ`€rcTµ\ÎˆQ·–©Ð\æÁŸ\èo´‡Z‡v\nû&I \ÛQdc6º‹\'PžüŒ¥\íF„@†%°\Ì\Ï2<¥\ÇQ-noW¤½¯7P\"\Ö$¬\\Jl%S\áH\×8&=rö\é>wóÞº‡¨9oô\á\ÃY\Æg„\ëñ³Js\Ù\Ñþÿ\0“\Z-gD4L}\Ñ6@\Çq’\íE¤\ê\æŸ)\î)i\Þ\Zó°‹u:\ÊI[“°ø\ÜBh¾÷œX\È\ØZ±0\ÅQ°„\Ñf’œb¡\Ù\ãzon/¢\æ\Þo°©\Éµ\Ù\ì\ÇQ«iX¶\\~Àå¿ªþ…\å£	°°\ÄKLB¸\r\Ü]u`½|\ï\å}ýu}\æ-¯`\ëw½¯rY3Jæ¨…e2ú\ë>b	L$imBˆ\"š(Z\"~\ë\ÛB\àô?\'ØŒ½	3!)Z\Z0”„‰	»}Zõ\ßn¸•m¤N®C’96…`­*‡\åz¡)\×T£¨À6²F–§hA’³?†”M<n\Üô^À.l6\"…Aô\Ï`\Æ)\åj=‘‰Nj\åúT9ý­–K¸F…Î®žq\Ë`\Ì\Ú\r\é\æöO¤ùð\È[\Ý(3z¦p¨‚\Ú\â\è}\Ý5‹©N\à\ï­\Ì\í£Ê´]1^JHM²\Ø\ä\×-\îùŽ\Ô\ëø±\×ZØ´¬¸^À9	I²º\ä~7¢g\Ü!}h¦\ã³\r.¼ºV\ëi&û8’\í\Ü?ÿ\Ú\0\0\ä\ëºJó\Ä\Ñ4£boz\Õ_³óR}c€\äg}*\ßX\çgú\Ý\è\äl¬Ó˜i<+õ¾3Ÿ\Û8­\ËÁ67\'<€m€›6|†\\\ÄÛ\Ë\æL-\n“‘\Å\ÐMc>\Íõ\Ø>\É\Ç?ýeö6I\Çÿ\0«¹™M†=9‰›[‰\ã#|<o¢»ž,\ä4K•#‹\Úó—yÔ’vd\Ï\Ì×³ñs\Çù+ùY)ñ\ï\\\Õ#¯¡jGž>\èO£yJ\ÛŸ.eyN µ\r\Ãn\Ó\Ã`’¼i…®l÷cˆ™\îXœå¼¯$29›SÚ™f\Õr/\n\ì\Õ#tUeøþxd‘²•½½À\'NÐ®r1BÛ¼«¬šl\ÝÏ™\ÓÁFÛ¸\\\Å8\ã\å\ÐúÔ©ü*Ÿ\éM”rü\Þ\n\"™q†¶w\âH\ï\Ëo0\Ô\ÞVªþV²/P)~Á]Š\ÏØ§‘IfIL]Í¾Dñ¼OK\á\×\ä¯ü(/ý¢\æ\é¹û²#\É[ro1;^õ‹\r’ä«¯°ó797Uh‰»•‘û(½:D\éJ|©\Ò-ÉŠß>NTZmj\Ü\Ï-5—K+œUzð\ÍD±\Ò3|µ\ß1+W*è¤–\×7²³ý\É\ÓÓ““‚D\Õ]®\Ä\æ~Á\È5µ9{‰Ò­—T™ô\á±\Ç\ï\ËþG¸·\Ì8¾L/;\n²\Ç=¥l(± \Å{¶\Z±AöŠ±[–žWJó”P\î=V\ç±E³(/\ÏVNG”\"!\Íä®µ¦+M_$µÖ©2X„ ƒ]ŠX‹\è\ÐbcUH$ŽJ/-^OŽm˜\ìÕ–»Ù+\â0Ù³TÔ·<W|g÷&d‘¹\ÎikFPŒ•\ä>KuÄ‘Sl¬RVc\Åuxe2Dc|U7( ‰‹\ß#\Ü\Ð\æÖ³\ä³]¥\\\á2û¼}ƒ_•\Ý$6\ïAr\Z\r¬\â\è£+\à¾\Û„~è¸˜™¬!\ìvöÉ½“1›\ÙúD ™%‘?;¢o´)$;¾\ÇVz—`ä…ˆý®’V\åf\å«Lÿ\0Ÿr¯?Uç®¦\çy	\ÄN÷6B\æ\Øo\îG\'Ö£\Þ\"ö»ŒŒ\Þ\×S{\\a#ný£—\rd\Ü\Üo±~¯ñ·>\àl2G¥x\ë•âªœ\ÈVCT$\Ë!Ø¦ýn\Â>‡~[\çCvþ\ßžž\Å&\Å0­\á\æ6ü™?\êÅ¹\È\ÎyEþQ“CùDMüp\Úÿ\Ú\0\0{P\È^2Aö5\í9Ô¢\Ü\é?£µ\Â#@J\Þt\ÂÆn\äapBN®\Z\î\Ó\Zcó\Ì\ÄXWŒ­…B\Ê\Ê\Ü?v™Ð·*Hð¶\èB\Êp+=YN‘g)¾™X\Ó(½«1­±”k‚Ÿ	jr2d7•½y‘Qq+\Z7Ñ¡Hý¡öœQ.+j\ÚP\Üy\"g¬,,jô.À–BòÑ¦\Ý0²¿¤Ãº=GVò+2¦7 ·²\ÂôS1aø\0@a<©XC\ÇHN…ƒW1aac¡¡H\Þ\ÎhpsKP\ë=XXDea :	\Â\Ø\n-:‚Î›P\Çä•¸.L=ü¤/ [š·5yB3”G\ãt\Þ0‹p·,®Ë²%e4½‡\á\í¥\Ô=\nì½«Ú½«Ú½ªiÿ\Ú\0\0\×*‘#\Æ\Æ\ÏM’±\ÃF\ÉHG\Õ¬,i•*Z09¼¤D?•n\í\ÇA«z1ù\Ú5\ÇF5(þ&³(·\Zemd!¦Ð¶©e\Â+j-X\éÚ™\ÂzkQ~˜AŽ^7¬=yN›´Q¬k±x×xŠ!\Z\ê^¡‹yŽ›Sk4/^ ŒaUøCNVVVu*1’¹\Õ\à\r@hOu\ê‹!aø¯*©Äš\Ý\\2³\ÝeJÀö\ì \í\ë\ZeTc*›ƒ˜Š\Z¸¢4{7¯˜@é•ž‚\ì˜]‡F\ã¡™²²³\n\'VwG@Q\Ó+8Y[‘:FŒ¦Y-M‘¹jkpœpÏ„\í\Å\Ý\'ª«ý­j“ô˜\Z\à+ƒe²R¾3“j°#\Ù8c¬žˆ\ãö\Ã&@÷\r‹e\Ë.C+jy\Øûþëº©ú«3\×k:ÿ\Ú\0?¼\æDZEŒU!{f¶“`™j\ÂG[Œ\ÅJkÿ\Ú\0?¢p½P~†¡¼úu¼²1Ö¹\Ñ\íQŽbNrCln‚-y t\Æi\È\êrcQ\n¨>‡ÐŸe\ÖL\ä™)\íÿ\0ÿ\Ú\0?¯!?–\Ö\rÑ‘‰¦r\Ù1zdiZ\í\àÁ¡¥ r\çš3Y›t\â“\é\ÖZ\ÝD©ióZ¬N\èQ¡L\Õ|\ÏM\Ô\ÖjZ\Ò\æ¤óH\\%\Z(}>“\ÌUS]A­\Ì’\ÕYñJˆfŽ&§·T\ê&ôiµDÞ¢qKÍ”>Ÿ\Í:KSC­F\åifI£MHðŠªu«ª\Ôj*(g—|\àž\Ë0…\ÏP\æC~Èžh0À¤‘}«-\Ý>Qt\ïŸfj”˜[9Gö©e²”IT(ú:•:i·SM^ü­\Þ6@D\Ô\éª ŸIÃµ“\î+dR­\æ\ÞaM\è«Qif2\Âm(JIbSQ\Û\æZ‡å£¥ª\ÇÁLyu\Z–=-5$q\Þ\ÙU,\ÈG\Ë\ÎWˆ¾\è+2³ö…†¹-^\ã¶-Á|Nvl1ÔŸ\n\Ú~Ž‡“Sii©ºj|\áþ9©\Ñþ2&{·ö\ì0\Ä\nÜ»ñŒô´•LÅ­\Ê8©\Þ\éÿ\0(2Ó³K\Ý!¿¤˜ù”]\ÖR;-—\Ø\ÄAé“™yéº”q½ZGô,šfUooRÜ©»iý{¡©\Ò l\ÍP\Ú\Î\Í\Ì\ìq&&†b\æ\ÖQ6¦¾\Ï{›”o’¹§Šž?ý\Ì„G§£Pó5‘þf\Ì\Ðz®õ›\Ùwbg\ì&…z”¿k4\éY¯“\å\Ôñ*$|D\"èž¥Fnzu\ì˜<^¨«Q\ç\Õ*J¦3”qðUNGýño|_/±i”Y\Å­P(÷ñ‘8h‹–*6!„T\ÐhšY¼ÕúþøÏªe±NaL™Œ\ÞñŸ3w˜´Å®|e\ÔUGŽõc\ZŠ”\ç\ß\è|Á_\áx©©\Õ Í•[4Ál%Rö»Ú°F\Ñ)\ÃE1²>b\Æ \Ç\ç¶©0r¡; ŠG¦;¢n\Ó&Tj_\ÞjŸ£¤\ÞJŸ3\Ï=VÅœ\Þa˜\Û`Ê±\ÙdH\êD~{zbE˜lž0Z•PJÞ¦\Ã\\ýùL\ã\Ëü¾­F\é.\Éw\00\ìo±\Ù\åúv\å\Ó!­#ñ”>¥… ‚\Ô{O*\àkÖ¦sUj,\ìnûc¦+cšL%\ë¶/4\Ül‰VQPm¸Ç–j\Ô\Ù2¾› KÇ±¿BÌ«™Àùk\ß\ZJ¯u}+\"œ3¥¢Nœªƒ4 ö­Eµn©O]‘Z¾’§U”õib{\Ð\Æwã·‹¾2ôh‰`\ËŠ¢v\\¦w\ÇN­Ž¼.±˜4\Æ\Øn\è\íº.û\n.\Úaˆâ°³û\Þ1¦2\â¥W…½tN¥\ä±(9X¨q\Å#|7I\rB‹™€¾\rJˆ•\çc­a3g}öE:Th}*Lµd[˜á€€ùFq\í\ÛE[\r\âB*M¨e(+^’Ô•ù…±õ:1ø\èÿ\0„Z-\Æ.Œ§\Ã\ìg¬r«~R÷\Ãü8ð‹šõ=ñ’ª\ËaÀ\î„\ÍË˜fž\È4ªƒ%k{\Ä\n.f„‰7\Â\ÐiÔ°‰\Â\ÚÀ1“e¾FøÉ“%\Ê@œ£7\ï…2•\æi\Î}†2µ»!Ž_˜£„\ã¹[,,/¤À¤\âdXd53z™Fg9F\Ì`Iö›bnsd70 ø\Åo,Õœ•©±+›¬\ÛGQH40ôf\×2Z,q\Øýð¯_Nô5ZA–°qÌ£q/\Ô<ôr¶ñ	ŸM-b€¦¸k„:\Õ\Ò\ÕÕ¹\å§L~¦—\éÖ3b—\ÍQ†Ü¬KOGM\n\Ë>²š“™E\ÙqžØ»¤‚À¦\×ñ\Â*.,„Xi†œ\é‡Ã» \ÏÍ²øl…s8“\Ù?_`=’L/05~MiJ ÷…\àÀJœ\éq7\ÊF`0ý±RŠ\×~•6*\0\Øôyf—X\Íc;\ÒUi~$”qy©ø+\ÖîŽ­%)T\\\ßQW\Ö\'>U§³”ºšŸ\ÖLe÷i\0Ÿ²3Tba˜(o&\Z$yLLs,,ð(j#\åŒv\Ãd\âD”\ß|K½ƒÞ¨r/¢p\Î}8.ó:*\Ú\Z\ë&ª\'5ž1–•Q^\ã\Ój¡”\î‰÷[³	´_\×ÿ\0I\Ïo\åŽfð”l…¦–“\êï…¦,¦,ž;á£Š^=–]?t\Ü\Ø\Ãþ.=\ÐÞ¸²7½Á¾F*õ\\„\ÊsnôB\Û>¼À,Yü†\ï\ÄA–\Ëc„¾,Sê‹ž=¸¹\ã\ç/1^_™+gÿ\Ú\0?!qKQû=Lg2l¾ñ¸4RAY «)½Ò³V³^w>…¨´\íF¯\Ä4€y†ýy\è¹UU½’ù‹ºsâª©}ÁõC7´\ÙÓ«@\Ã«û¥\ÄvŽ€a\nø>ny’i–»—\Zic\ÔI¬¡iôl®mOò`™M5¨cŠ³\Ð\Î+©d•ö4EŠK:‡|ª³\Üg‹\ï0F˜F¤f\\Œ$©©#hÍ+5X¿ˆ_\ÐõƒG«\Ê7\Âù†…¯¾jÃºW‹%ü\ÊZ«i|G\ÊËµ\Çgp\Ñ\ÌÈ„³\èt<\æ\r\Ü\Ôl–=\Êly?0\ß\×\æžOù¹\èQœs@\àøD²s,\ævh\æU=,\Õ_A4Ê‚?%*|\"W¸3D\Ó7\ì\"tþD©ðôÁ\ç™]a\ÉüT$Š¼¤\é\0\rgþ&¶\\\Ó\í\è\ç¶~s%«K(\Ì=w\Ó¤¥\0)bk\È^\Ã£)u‘ñ¾ò‡\Ýz¾/O¬¼mrð®&wIM\Ì	\Þ\Ç\ÜX£]\à¾]ó³\ãa­\í\ì—©pÀ\æU\ÐÐ°–f¿\"V5u\ßõ\ã\à\ãôh\ßs92ñ¯¬\ìµ>^»4Á\ÏW9\ÌÚ¿Ùƒ\ê1Vú³\Ð\Ý\ÌO\Úò.mrO\'Š<¢)J­Þ <†©ðÍ•E[XÁWó(ð‡Ü¨5dwD\Ð|q6öYÀ9W\Ì(9e\î‹ð?\ä°e\å\")_¢©O\Ë>[žö<SH\Ì@–Z³\Û2\á¼\å\à\çÞ¡f³žU´{b’üfe 9bˆ\×F\'\ë¶X$¶ü¥¥¡\Î\æK\â6p|¢ñ\nrcwm\Ú|DFwòb\ÓÁp¢”¼û_\à•\\s\Ñ\í%²\éu–_HA”ƒE¬?d:v\ÅPhb@\Ý\\V-\ã«-|Þ¼M\0•\ÝU\ÒCcVsž%OAÿ\0ñ—¿J\îˆý\Ë\Ùt\Ò?³ø\'(70¸–\Ð[Š\Ô\çN\r/øÌ´õ\í\È\×\æ«\æSN\ÚÊ¥²\Êù—MÃ‹™lmAD\Ý\Î8\"·&ÁkÙˆ!*\'0^\Ðs\Ä\n$v€{‡½A+”°\\£A\×.‰¼\æ)4Ÿh¯?§¨\ç\Ô5­A¶Ž\ç*i\å‡8\×vh\Ôç„ª³\é,+\ÌPtY\Ä\0µÀ§Á€t\Ö\ã¥D\È\0\ä6\ç©yEg±ýªh-Õ±¢\0{6O¾jS0\èL¯`\ænWÇ´´?\0 ñub\Ö<§p#aÇ¼o_#ý\Å]\"»2\Î!\Ü	e$¾3ð ™½C·ýK\æÎžNO‘\ä™9¹,‡jcñ\îFY•$²›·g¹.žÊ³\ïL í‡«=\ÊcB_§€äº—X”\Ð2þØ•\Îrmq£\ã\Òh¶/i*	\â\æM­L×˜#zy¾\\cH[rÀ,¾X\èF\Ï,7\Ä\Îkßš\ç\æ-Pº\äþ¥ù…½‹=\å5\Ô\á^|Á\éÐ¨N]}dÀü÷Á\ÆN\Ø\êÁx´\çÿ\0R\ÎZ\ÍOpÌ‘ˆ/=¿#ö–½Ž26µ\Û3+\ì¥‰h\æ˜ÿ\0)8\çQ\ÓT¹\Ñ\Ëó9\î’/E\ë\í+¾–†\Í\ÑoÒ¼L\\\ãðN\å×º]6¯÷³}\åX÷¡h*÷•¬SQ\'Á¦QSNIg!Žš—˜\Ç\à\Þ\Ð\nºW\0k\n\Òx¼ž8\â-h\Íòþ²\éƒ\í(ªp‘õq{_#u\Ñr„â™Š\ä\Ù%2«°#\Þ\ÚZ±\ÅR¾$\Ú\È]¶«67c4~	;üy”ep\êb3\0kÁC—>Ò˜\Ø7\å\æ\È0$Á|&‘÷\ÆñsU\Ó|\Ù?A˜!K\ÓGp\'HD¥¼&M\Ëj\æ©Ùœ,\ÓW“e[Z?1nUÙŸ\êsÁ/\Ûÿ\0¸+Åùœ\Êòb4\'-\é\Êö›“\ï\íyœm\ãðM5ð\ÂS|37.•2¿T~`N}\Ü9®©6\Ó\âr\Õû\Å\Å\ÙK%ã–”øÀ\Ìñyž§\ï‰\ìVøE>\Ô#\Äü©ù¿¹\íûb\à¿HÌ£‘¯Žf½U9\ï÷\Ü\Í\Ïÿ\Ú\0?!L\Ê=\0ŽŠ˜X\Þä¤¿^\×0‚L!P¿§]B™w\ÑIPMD12Á\ê\Æ_A‚5IL\í	R¿Uzsú+bøôXð‚\Çü‚,¶¬µ‰\Ê44ˆ\ÎJ‰±¤nÞ\Ìó…¥þ‹#$nZLXÁ\nz$\Ûô›$1œ\è‹\àôZJ\'œ¬L^=8’*ŽH7Dj6l¿ppôŠ×„%IQô©^»\ëÉ˜@úh	\éZ…\íoˆ\'ü\Ö	\ÅôBoKË„©b/\ê}@€U\ê8ú ¨ôA\ÅT[*_r\Ìú¶ž\èõ™dT¾\Zô*Vj%C\×	r¢\\)))r\ÝÁ%úv¦¦Dˆ„2zY£z&Ù†˜ô\çÓ˜ú°eú%¼Fž\Ð\àÀ–l3ö\Øú7\Ä\Ê\\V¡\èF\\#Uz—¢\n®%\Û!x\î—\Úf±~ˆúŸ\ÐÄ¤vO)^•EMzŸÿ\Ú\0?!\Å\ép€Oy\æ_ø@q1#V[=hfež’šj0~ƒ\Ñ‹õ.P\åŽå±³\âQ¿1‘mõ\ÛÐ‹„¨EJ•ù\Ùú Ü©^Š•\èAþ)\êTl¸†\×û)H@1ôžAkq\ë\è[ôT„f\ÐS‚3*pt\ÊvN\ër\ÖÛ•¨D^%¥\á\ßNÇ¡0\ÅÇ£\Ö\Ô\á |E˜-“rC.R’W¨\Â@ýJ\Z%Šg9˜uÁ}5B_\êc•²\Ü\å­TŒÔ§qW4[!\èz=\Æ-l\Ì<œo\Þ\Ñ=D™I6ÀT²xJð\Ëô—.\\\ÔzG\×\ÙÜ´}ž’\Òö\ã\ÐÞ£p\Æ-óQ\Ëô[\ÕY\0	u™z|\Óa¦`\Î;ÀA²*ñ\Úv™1P¶\"%ð\èƒMM\Í zT©P^\Ò’^\ÝÀJf~S&›\í&\rûe\Ðd\\ûÊŠ%ƒ“\Ä#>—\Ôz;~\çeÄ”J8ž)n\ádWôx˜Œ7\é˜C“¬P-Eñ/Ä¿üKb\ÆõŸÿ\Ú\0\0\0\0	\Ì\è\ãó\Ñ\à-@>\È_ý8› I(þ§G¶\0\0\0\n­úE§@º…\rh<¸ºh\ï\Ì]$ü‘\Õ>z\Ìßœfa)³ù.o^/ÀDµ\í$Q{‡,˜<¡»ó¢X\×uP@x€Ô—\Æ^i\Ã1HlS»\îÇ©w\ÝAuoÿ\Ú\0?¯;¬+\Ì@¼¸L¼\ç\Ôp²Z\Øª#\\\ÈÆµªeZIf\Ü\Ð$3\Ñ32\ç8*BÔ»°¨\Ë<P\Í*\â\í>#hfu\ÄË¡Ö•f\èv?IOK\ÃÔœ5»L/*›Af”©qOU\ïŠÝªP\â;ˆJ\à‚\àñþ–f¬F\ÇVC6+OL¨\ß&M²\ÓÁ\Ôj\Å\ÈLu@(0c\Þ*\ÒV\ÈÀ«3\Þ\î6¸¤ÀTBª,FÊ¼\Ç¢¨tX\08\r\Ê\ÑB”XT¦\É\Ût\ÇM+‡9¿š ¶,œpk:—\Ì\à²]]\Í4Ê‡Wr\Ô\Ø\ÖJA†·\Å\è:\Ü\íZw¥c‡©\Ïô¿a!V¹B\"B”€\àœÿ\01ô  `¾U–!¦\ì8\Ý8H\î_bGQ™„&\ãŒ\Z\Ë}_5o ¶Ó“¸\Êj\ÖLWTøKrS‰*8»™0R\ì6ök\ïPR¨½%\Ð\"µu“0\Ë\09(\ïz{FH\ß\n´\ÝY\ê\Z%Jý\é]ú,	\0˜1^¦ô\ÒV©v·\ÌYDxXU‘{¦ÁA.OyC\\qœ.••ƒ¢v…@r‘\'c<©\í7@\á[\é0f¤\0 8\Ù0¹­1£\Â@Œ¡X^\0.).\Û>xÁ/LšNœ¥8?ñL\0\ä,X‚¬Zì¶ˆ\åi\ée­¬¾\ÅCx\Î¤}\â|0‘\Ö3AMŠ&8¢‡òÞ9;\ÖS‰\Z\î\Ñ[ö›\èÈƒ«š\á“ÿ\0f¥”ø\Ç(Š\Â/O,¨Ž0\ÆB£Km>Ä‰C•\ÖÀVB\Ø\Ç0\à\ÅYZe¬ƒ.¬:‡J³\"BXUB;B•ø¨H½…õ–U\Þ=j³Z\0º\ê\àHGeacµü\\¦b¸(oœ\è”û›9&?ª+Á\â\0z;D+-s´CB(\×6_mupDXuB«\à3+\ã/(sH[¿\ä\Û\Ýo˜¥š\à«\ÙNz‚\Ó\è\\”\ÆuúD\Ø\ä,®—“ˆ½ù\Ó.—%—¸P.’Š@¿b^>\ã¯\ÝÌ›Iüp×™•Ð…`öJŸsôºÏˆfØ©;\ÉP\ÚMŸ˜U\à\Ì5¸Šg]u¡e›Zep\nÛ¼¹qÁ\î\Í\0˜\è?`˜uBE[eG \î<b\\&\ì\ÝYÀ\Ñ0Î¾š¼šö…„Á\Ñ\0c.‘[\ÙB¡‡\ä•bã‹ŠRS\Ãr¶B0…\\	U1\Ô ‰:\n*\ÑV\Ê\Å\àoUmüKsX%TL\Ï,§:‰M°—£«j(«º\â8s.)•K.£||gøˆ€\áx1|ð¶¢—º\æ¿Ášg\Ó\È\Z\Z­\Ì\0RÐ·\ÎÝ°V\ÊÁ|Z1{[01´\Ì“Œ\Ë(£‡¦€\0+HŒ|„yL¾Ÿ2»XZ\Û<Õ€\çl!¸\áœ.ó\ïr\Ç\â%NùÈ¥P®0\Ä8\ÌJ\æ0§3HÄ¢m°\×\ÎAjUJVi\É\Â 8{\0\n\ï6\ç\â\"¡·T\ËY\Õ{DJÅŒ•/\0„pÙ«0\æ#\Zk”¡“063À¦TmD(¬h\Ô\'‹mAAƒfÖ®\'ð\Ð\r°\\)]\Ë@<…Y=¦\ì¥ªõPUŽ\â\ZÐ \Ò<2ÁLs€ðXò´÷h™³1›*<¡\ÛsD2\n\rÑ˜\Ùh‚´R›²Š‡õb¥­B§7ÀÌ¦€\ëšcñ\ÜuJû±óR\á‹¶}\ê)\'\ÑZ®\0#ú`1:f\Z”!š€¬\à\êì³‚Á‚\Ú>òys0n\Ì\ã¢#\æa“]\á›\ä‚möm‰\Ãz	-AÀKiœW\Þ\n©W«¡\Ó\Üc\É\Ø%H\åÃ³\ä\ê.¿{,:q\ï.\Å\'M³\î>Ð¦\Í \Ö\Ùý@JF\Ð\Ô2Q#Z\ÙwMz9ß½X–\âùc\ê˜JµH\Í\ä\Î#\Ç\ã˜\á;\ä\ÓLl•\02As\Ù`\0\ïEm/)-\ÃSVj°_Q5ŽÎ‰{LQ\Ód&¡h”\ã¹I-š\nÊ•QÀ…\ï\0dª\Z9º¹I1\ê.ƒk\\KZ-D\ï-µ\Ì\Í?›—‹—Ž\ã\ËdY\ÈS\ß9˜\"\Ê\0^Z»\æ9\ï]\æ\Þ(\"kÒŒ„KB%ñ\ë(	’€9­ˆ^P \Îm\Ç\Ð\Ü\0q\äÊ¾&\áb\Éq0sƒ\\T>Èh_ffŽ/°®œY.#¨UƒMÖ‚=2øŒ\èP\08\ïqK\Ø<¸i6\çP•ž‘—g7J£U99;*+./xrxJaŠdõxÂ’„Šá†´-Ó”DŒ.\â\0\Þ¶\Õ(\Õ\âðG³E­i•\Ïr°dœø$.5Š\Zh³\r`\áö\î¡.¼–·\Þqeô”^¥@8/&=\à’*so÷¨ 8®E\ä)1“O\Ó?$Er-ž\ÊE$Q•\ÚB\Þ@³‰PÅŠLÐ£³Zj¼ú“T4‹·Jˆ,PA s	p¡6¦[8\élÒ«õŽ;À\Ö/€Ï¤š·¥‚Ú²B\à^Y\Ä609˜\ÙU\ì‚Ü°-¢\Â\Ö÷œ\Ä0\Å8„¸\ËD¼—¨\\T\à=¸§æ¿ˆwÐ±6»ø9%	QMx¹¼\Ë\Ç&\êUhMh\á\\ˆ\Â\ÕRÔ”n\Ûû\ê\î¿\íCZ™¡žŸˆ¨BRv#x§DP*au+Q\î\æ@FÃ¼	B\êU“€V½2³š•Uom­q\Ö!&[@+Üª¾cx\í_\Ê\0¿2\Ö\å|…_x|«8\ï6~\ÑNA\Ù_™‹Ó ƒšœX¬e¼enªKó4\É_3Ÿ¤º¹\Â¹‰\ß-\íÎ³\Zõ÷\ësCróqñ]Ty›L+F»nª\Ðg(0^y¾ mW\ÛüB‹Ka}\í}\ï\Ì6\"‹d0g´)cÅ‘MŸ	Ô¶“kG.#%7µ\×}\Ëa‘‹\ãi÷–œ-¶Ì®\Ü2±\Òÿ\0bS]%›ý	\Üßºª´¹‡Œ0ñx—wyÿ\Ú\0?D	²5Ub`Zù\Î?n\áFóe«¶tl0L\Ã\èôÂ˜x\ì@\Ñ{—•ƒ0±.“&%¥ó\è\ê8\ZCs\0\ì%œ‰m\â\0Ç¢¬%\ÂuP\Ôq\ÉVQ*gÏ¤™Š7*1e2?AR¢\\\nž^…4‡ª¢i”T\à|}\É\×üM’>\"u,\Ä\Í\âxÄ‹\â[H„\Ä,6\Æ3Ooö=•~X\Ãl&˜&C\ä),p\Ê\ã÷\ï)ž‡3¢¥™%B[`˜ ´³\Ñ\ç]J”¹\Ìþ!\\Ý³\0\ÜT\ã¿Fnˆ¯\Ç¼ŸÁ)‚c\æa”0\Þ\ÌN\Äð\Êx34ð$¹¶0e\Û\ÔG>c¥\é,³±A”%{\Å\é\ÌZ—\â6‹ÀòÁhº‰™¼B0”\ÌLL& µ(\'*Š\æ\\J\Ý\â	\0‚·hD•S,\Ï\'*7‡\è\Ën\Ú\0@\Ä\ÚT¨‘+\Ó\Ù˜\Êb-_´¡#An\à7\r1D«˜$Q}ûy”Õ­Œ\çb\"S\éM\\s‰^š€\Õ\ÊÆ‘CL\ïfWg¬1Ç Ž¢™.PSc\Èñ5¨\Ð\ÄMÐ¿Bt„ELK‚\î \ì\è‹\íf\njyu(\Ïq¤	t¹PT\Ö\æ#­¤!U\ZTaf¦ (Ò‘N\ãœ0K\Ù0\Õ^ø›\Ëû™(V%\äW5ø€5˜<‹\á+\ÞRk\å\"\Û::\æ	ƒ\ÛÏ¥T%\ÊOC<C‰a;‚†SŸ\ÂQõK\ãE\Ò7gÞÿ\0ª`J¥k„\r¿\Ùå¹„ù–ª‡1#D\Ç£L\ËpŒ—7mL1 \"Ì»\Ì!O(\rú$†¢cª¯¬\Ó\Ö×ŸKq3[\ëÑ®eZxÁGu&œ?¼ÀT\å•\ÛóGó<Ÿ˜vþbW	÷‡._Ç§ÿ\Ú\0?,#1jð\ÂEŠ\é\åA\àyeM\ÐU;úg¯‹J(\ÂQ§³Ã«\ÆrŽ4”üz\à0\Zdq\Í<x‰´Õ±\à—A\æ®RK@©R‚ŠŠc:\n‡e\Þ<‘`7H¿Œ·^Š(\Ù}\æ\ÒK}q\Ô©¾bC\n•\è\Ð\Æ\Çü\ï[¨ú\"\Ìsa<\ÒS¸yB0ƒ@^bñ$¨C4\\¿Á\Ì\é£Dg*óþFò¢M÷ °a¿x.«“PÐ³ß‰QY\rb%\Ç\è¢o±ù‰~\Û\Üw_†-Äšýø:=¥—‹À,n\ÈywX#)\Z \Û\0§£mÊ‹\é¹\ã\'§re°‰¬s1\Ñ*\n²ó?(.\rûM0–J#õ\riô…hz%®‡^‹Â³¾$Š‰pø`Žgp(|÷K¨\0\Ò]Y\"‰ŒEÀü\êK³OE\n0lQ\\u¨\æ\0\ß\"L \Ê\ã\Ú(\Ç3…^¡ñýÀÇ¿4\Ñ)þþbg˜A¶qA6ô¸\ÉaŸ|M\È`9 V%¢KrÜ»„sR!»®¢{µ÷\êb•\ï¹s™G*¥¯»6ƒ!!‡¡!˜4­Á/8úÍ©>ƒû–»<­žIz°Š¼ÀŽÊw»–D]¨«*¶KU€ò­\Ì\æ%ˆ=\\·pš…\Ë\ÇeX3©³?HTBù—¼%1õ›yÀÿ\0$\Ê\àùÏ´5«˜‚œ¸†0ƒ¶aƒu\Ö&¿<DeG.¥!\ÄG%\ã\ï, ¦G)´\ÌK’ôza€vþˆÊ´õ\Ã*¡I.züO°¢O£0ŠžR¡\íŽ\Êù¯\Ä^‹y/\æ\Z\åD+å…ˆ€s*\Ã3ƒS\ç\"Zó¨\ÒF¯ˆµP½¸†“UŽ\",Tp*ü\Ï\ë\0Ž\åb\×	­ˆý˜€z\çq;\Ê\ÑÔ¦±‡X²¨©˜\ì\ê\æ\Íä˜´bqqÿ\0õ/Àûq}>¤_O©\'|\"ù³ÿ\Ù\0',1);
/*!40000 ALTER TABLE `consentimientos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-21 22:14:21
