import { Component, Input, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { ListoryService } from '../../services/listory.service';
import { Listory } from '../../services/Listory';


import 'rxjs/add/operator/switchMap';


@Component({
  selector: 'detail',
    templateUrl: './detail.component.html',
    styleUrls: [ './detail.component.css' ]
})

export class DetailComponent implements OnInit {
constructor(
  private route: ActivatedRoute,
  private location: Location,
  private listoryService: ListoryService
) {}

private listory: Listory;
/*{
    name: "Modadaki Tombili Heykeli",
    description:"Yaygın inancın tersine, Lorem Ipsum rastgele sözcüklerden oluşmaz. Kökleri M.Ö. 45 tarihinden bu yana klasik Latin edebiyatına kadar uzanan 2000 yıllık bir geçmişi vardır. Virginia'daki Hampden-Sydney College'dan Latince profesörü Richard McClintock, bir Lorem Ipsum pasajında geçen ve anlaşılması en güç sözcüklerden biri olan 'consectetur' sözcüğünün klasik edebiyattaki örneklerini incelediğinde kesin bir kaynağa ulaşmıştır. Lorm Ipsum, Çiçero tarafından M.Ö. 45 tarihinde kaleme alınan \"de Finibus Bonorum et Malorum\" (İyi ve Kötünün Uç Sınırları) eserinin 1.10.32 ve 1.10.33 sayılı bölümlerinden gelmektedir. Bu kitap, ahlak kuramı üzerine bir tezdir ve Rönesans döneminde çok popüler olmuştur. Lorem Ipsum pasajının ilk satırı olan \"Lorem ipsum dolor sit amet\" 1.10.32 sayılı bölümdeki bir satırdan gelmektedir. 1500'lerden beri kullanılmakta olan standard Lorem Ipsum metinleri ilgilenenler için yeniden üretilmiştir. Çiçero tarafından yazılan 1.10.32 ve 1.10.33 bölümleri de 1914 H. Rackham çevirisinden alınan İngilizce sürümleri eşliğinde özgün biçiminden yeniden üretilmiştir.",
    image:"http://www.kurandakidin.com/wp-content/uploads/2016/08/Heykel.jpg",
    page:null,
    listoryId:"5",
    category:"Artifact",
    owner:{
        name:"Onat Bas",
        userId:"4"
    },
    createdAt:"8 minutes ago",
    time:{
        name:"Years",
        units:"YEAR",
        values:[2015, 2017]
    }
   
};*/

  ngOnInit(): void {
    this.route.paramMap
      .switchMap((params: ParamMap) => this.listoryService.getListory(+params.get('id')))
      .subscribe(listory => this.listory = listory);
  }


  goBack(): void {
    this.location.back();
  }

}