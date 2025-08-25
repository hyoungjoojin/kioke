export interface Profile {
  userId: string;
  name: string;
  email: string;
}

export interface MyProfile extends Profile {
  onboarded: boolean;
  createdAt: Date;
}
