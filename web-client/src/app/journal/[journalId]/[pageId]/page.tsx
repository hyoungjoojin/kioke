'use client';

import Spinner from './components/Spinner';
import EditableTitle from '@/components/features/editor/EditableTitle';
import PageEditor from '@/components/features/editor/PageEditor';
import KiokeSidebar from '@/components/sidebar/KiokeSidebar';
import { SidebarTrigger } from '@/components/ui/sidebar';
import { usePageQuery, useUpdatePageMutation } from '@/hooks/query/page';
import { cn } from '@/lib/utils';
import { useSession } from 'next-auth/react';
import { redirect, useParams, useRouter } from 'next/navigation';

export default function Page() {
  const session = useSession();
  const router = useRouter();

  const { journalId, pageId } = useParams<{
    journalId: string;
    pageId: string;
  }>();

  const { data: page } = usePageQuery(journalId, pageId);
  const { mutate: updatePage } = useUpdatePageMutation(journalId, pageId);

  const user = session?.data?.user;
  if (!user) {
    redirect('/auth/login');
  }

  if (!page) {
    return null;
  }

  return (
    <>
      <aside>
        <KiokeSidebar user={user} />
      </aside>

      <header className='absolute w-full p-3 flex justify-between'>
        <div className='flex'>
          <SidebarTrigger />

          <div
            className='flex items-center justify-center hover:cursor-pointer'
            onClick={() => {
              router.push(`/journal/${journalId}/preview`);
            }}
          >
            <span>Back to journal</span>
          </div>
        </div>

        <Spinner />
      </header>

      <main className='w-full pt-16'>
        <div className={cn('bg-white max-sm:w-11/12 m-auto h-full px-16')}>
          <EditableTitle
            content={page.title.length === 0 ? 'Untitled' : page.title}
            onSubmit={(title) => {
              if (title !== page.title) {
                updatePage(title);
              }
            }}
          />
          <br />

          <PageEditor
            journalId={journalId}
            pageId={pageId}
            content={page?.content}
          />
        </div>
      </main>
    </>
  );
}
