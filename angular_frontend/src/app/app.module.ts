import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'; 
import { HttpClientModule } from '@angular/common/http'; 

import { AppComponent } from './app.component';
import { AuditSubmissionComponent } from './features/audit-submission/audit-submission.component';
import { AuditHistoryComponent } from './features/audit-history/AuditHistory.component'; 
import { AppRoutingModule } from './app-routing.module'; 

@NgModule({
  declarations: [
    AppComponent,
    AuditSubmissionComponent,
    AuditHistoryComponent 
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule, 
    HttpClientModule,
    AppRoutingModule 
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }