import type { Role } from '@/constant/role';

export interface Journal {
  id: string;
  title: string;
  description: string;
  pages: {
    id: string;
    title: string;
  }[];
  isPublic: boolean;
  role: Role;
  collaborators: {
    userId: string;
    role: Role;
  }[];
}
