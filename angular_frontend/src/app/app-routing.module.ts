import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuditSubmissionComponent } from './features/audit-submission/audit-submission.component';
import { AuditHistoryComponent } from './features/audit-history/AuditHistory.component';

const routes: Routes = [
  { path: 'submit', component: AuditSubmissionComponent },
  { path: 'history', component: AuditHistoryComponent },
  { path: '', redirectTo: '/submit', pathMatch: 'full' } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }