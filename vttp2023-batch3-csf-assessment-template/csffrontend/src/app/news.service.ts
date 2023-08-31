import { HttpClient, HttpParams } from '@angular/common/http';
import { ElementRef, Injectable, inject } from '@angular/core';
import { News, Newsresp, tagCount } from './news';
import {Subject, firstValueFrom} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  // time = new Subject<any>()

  time!:number

  saveTime(time:number){
    this.time=time
  }
  retrieveTime(){
    return this.time
  }

  http=inject(HttpClient)

  upload(news:News,elemRef:ElementRef):Promise<any>{
    const url="/api/upload"
    const data= new FormData()
    data.append("news",JSON.stringify(news));
    data.set("imgFile",elemRef.nativeElement.files[0])

    return firstValueFrom(
      this.http.post<any>(url,data)

    )
  }

  getTags(time:number):Promise<tagCount[]>{
    const url="/api/"

    const params= new HttpParams()
    .set("time",time)

    return firstValueFrom(this.http.get<tagCount[]>(url,{params:params}))
  }

  getArticle(time:number,tag:string):Promise<Newsresp[]>{
    const url="/api/time"

    const params=new HttpParams()
    .set("time",time)
    .set("tag",tag)

    return firstValueFrom(this.http.get<Newsresp[]>(url,{params:params}))
  }
 
  
}
