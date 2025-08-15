import HomeContextMenu from './components/HomeContextMenu';
import {
  ContextMenu,
  ContextMenuContent,
  ContextMenuTrigger,
} from '@/components/ui/context-menu';

import './styles/react-grid-layout.css';

export default async function JournalLayout({
  header,
  main,
}: Readonly<{
  header: React.ReactNode;
  main: React.ReactNode;
}>) {
  return (
    <>
      <div className='flex flex-col py-9 px-10 h-full'>
        <header className='h-12'>{header}</header>

        <ContextMenu>
          <ContextMenuTrigger className='h-full'>
            <main className='h-full'>{main}</main>
          </ContextMenuTrigger>

          <ContextMenuContent>
            <HomeContextMenu />
          </ContextMenuContent>
        </ContextMenu>
      </div>
    </>
  );
}
