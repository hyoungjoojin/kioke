'use client';

import ProfileAvatar from '@/components/feature/profile/ProfileAvatar';
import { Button } from '@/components/ui/button';
import Icon from '@/components/ui/icon';
import { Separator } from '@/components/ui/separator';
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuAction,
  SidebarMenuButton,
  SidebarMenuItem,
  SidebarTrigger,
  useSidebar,
} from '@/components/ui/sidebar';
import { Routes } from '@/constant/routes';
import useGetCollectionsQuery from '@/hooks/query/useGetCollections';
import { cn } from '@/lib/utils';
import Link from 'next/link';
import { useRouter } from 'next/navigation';

function KiokeSidebarTrigger() {
  const { open } = useSidebar();

  return <SidebarTrigger className={cn(open && 'hidden')} />;
}

function KiokeSidebarHeader() {
  const router = useRouter();

  return (
    <SidebarHeader className='p-5 h-10'>
      <div className='flex items-center justify-between'>
        <ProfileAvatar />

        <div className='flex items-center gap-2'>
          <Button
            variant='icon'
            icon='notifications'
            onClick={() => {
              router.push('/notifications');
            }}
          />
          <SidebarTrigger />
        </div>
      </div>
    </SidebarHeader>
  );
}

function KiokeSidebarHomeGroup() {
  return (
    <SidebarGroup>
      <SidebarGroupContent>
        <SidebarMenu>
          <SidebarMenuItem>
            <SidebarMenuButton asChild>
              <Link href='/'>
                <Icon name='home' />
                <span>Home</span>
              </Link>
            </SidebarMenuButton>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarGroupContent>
    </SidebarGroup>
  );
}

function KiokeSidebarCollectionsGroup() {
  const { data: collections } = useGetCollectionsQuery();

  return (
    <SidebarGroup>
      <SidebarGroupLabel>
        <Link href={Routes.COLLECTIONS} className='flex items-center gap-1'>
          Collections
        </Link>
      </SidebarGroupLabel>
      <SidebarGroupContent>
        <SidebarMenu>
          {collections &&
            collections.map((collection) => (
              <SidebarMenuItem key={collection.id}>
                <SidebarMenuButton asChild>
                  <Link href={Routes.COLLECTION(collection.id)}>
                    <Icon name='heart' />
                    <span>{collection.name}</span>
                  </Link>
                </SidebarMenuButton>
                <SidebarMenuAction>
                  <Icon name='ellipsis' />
                </SidebarMenuAction>
              </SidebarMenuItem>
            ))}
        </SidebarMenu>
      </SidebarGroupContent>
    </SidebarGroup>
  );
}

function KiokeSidebar() {
  return (
    <Sidebar>
      <SidebarContent>
        <KiokeSidebarHeader />
        <div className='m-2' />
        <KiokeSidebarHomeGroup />
        <KiokeSidebarCollectionsGroup />
      </SidebarContent>
      <SidebarFooter />
    </Sidebar>
  );
}

export default KiokeSidebar;
export { KiokeSidebarTrigger };
