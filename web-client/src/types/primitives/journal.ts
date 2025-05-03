import { PagePreview } from './page';
import { Role } from '@/constants/role';

export interface Journal {
  journalId: string;
  title: string;
  description: string;
  bookmarked: boolean;
  createdAt: string;
  lastModified: string;
  users: {
    userId: string;
    role: Role;
    email: string;
    firstName: string;
    lastName: string;
  }[];
  pages: PagePreview[];
}

export interface JournalPreview {
  journalId: string;
  title: string;
  bookmarked: boolean;
  createdAt: string;
}
