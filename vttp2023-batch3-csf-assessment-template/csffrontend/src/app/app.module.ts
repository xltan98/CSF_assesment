import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http'
import { RouterModule, Routes } from '@angular/router';
import { NewComponent } from './components/new/new.component';
import { LandingpageComponent } from './components/landingpage/landingpage.component';
import { TagpostComponent } from './components/tagpost/tagpost.component';
const appRoutes:Routes=[
  {path:'post',component:NewComponent},
  {path:'',component:LandingpageComponent},
  {path:'time/:tag',component:TagpostComponent}
]
@NgModule({
  declarations: [
    AppComponent,
    NewComponent,
    LandingpageComponent,
    TagpostComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes,{useHash:true}),

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
