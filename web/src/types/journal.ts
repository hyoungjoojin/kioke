import type { JournalType } from '@/constant/journal';
import type { Role } from '@/constant/role';

export interface Journal {
  id: string;
  type: JournalType;
  title: string;
  description: string;
  pages: {
    id: string;
    title: string;
    date: Date;
  }[];
  isPublic: boolean;
  role: Role;
  collaborators: {
    userId: string;
    role: Role;
  }[];
}
