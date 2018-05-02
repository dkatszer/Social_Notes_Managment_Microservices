import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NoteComponent} from './component/note/note.component';
import {GlobalGuard} from './guards/global.guard';
import {DashboardComponent} from './component/dashboard/dashboard.component';
import {LoginFormComponent} from './component/login-form/login-form.component';
import {NotesPageComponent} from "./component/notes-page/notes-page.component";
import {AllItemsComponent} from "./component/all-items/all-items.component";
import {ListEditorComponent} from "./component/note/list-editor/list-editor.component";
import {ProfilePageComponent} from "./component/profile-page/profile-page.component";
import {FriendsComponent} from "./component/profile-page/friends/friends.component";
import {ProfilDetailsComponent} from "./component/profile-page/profil-details/profil-details.component";

const routes: Routes = [
  {path: '', redirectTo: '/dashboard', pathMatch: 'full'},
  {path: 'dashboard', component: DashboardComponent, canActivate: [GlobalGuard]},
  {path: 'notes', component: NotesPageComponent, pathMatch: 'full', canActivate: [GlobalGuard]},
  // { path: 'notes/new', component: NoteEditorComponent, canActivate: [GlobalGuard]},
  {path: 'notes/:type', component: AllItemsComponent, canActivate: [GlobalGuard]},
  {path: 'notes/:noteId/sections', component: ListEditorComponent, canActivate: [GlobalGuard]},
  {path: 'notes/:noteId/sections/:sectionId', component: NoteComponent, canActivate: [GlobalGuard]},
  {
    path: 'profile', component: ProfilePageComponent, canActivate: [GlobalGuard], children: [
      {path: 'details', component: ProfilDetailsComponent, canActivate: [GlobalGuard]},
      {path: 'friends', component: FriendsComponent, canActivate: [GlobalGuard]}
    ]
  },
  {path: 'resources', component: DashboardComponent, canActivate: [GlobalGuard]},
  {path: 'settings', component: DashboardComponent, canActivate: [GlobalGuard]},
  {path: 'login', component: LoginFormComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
