import type { Role } from '@/constant/role';

export interface Journal {
  id: string;
  title: string;
  description: string;
  users: {
    userId: string;
    role: Role;
  }[];
  pages: {
    id: string;
    title: string;
    date: Date;
  }[];
  isPublic: boolean;
}
