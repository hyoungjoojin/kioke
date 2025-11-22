import {
  ArrowUp,
  Bell,
  Calendar,
  ChevronDown,
  ChevronLeft,
  ChevronRight,
  ChevronUp,
  Ellipsis,
  Heart,
  Home,
  Image,
  ListFilter,
  LogOut,
  type LucideProps,
  Map,
  MapPin,
  MapPinPlusInside,
  Menu,
  Pen,
  Plus,
  Settings,
  Share,
  Sidebar,
  Trash,
  Upload,
  User,
} from 'lucide-react';
import type { ForwardRefExoticComponent, RefAttributes } from 'react';

type IconName =
  | 'menu'
  | 'plus'
  | 'filter'
  | 'chevron-left'
  | 'chevron-right'
  | 'chevron-up'
  | 'chevron-down'
  | 'arrow-up'
  | 'image'
  | 'map'
  | 'ellipsis'
  | 'upload'
  | 'share'
  | 'location'
  | 'location-plus'
  | 'trash'
  | 'edit'
  | 'settings'
  | 'logout'
  | 'notifications'
  | 'home'
  | 'user'
  | 'heart'
  | 'sidebar'
  | 'calendar';

type IconProps = LucideProps & {
  name: IconName;
};

function Icon({ name, ...props }: IconProps) {
  const Component = map[name];
  return <Component {...props} />;
}

const map: Record<
  IconName,
  ForwardRefExoticComponent<
    Omit<LucideProps, 'ref'> & RefAttributes<SVGSVGElement>
  >
> = {
  menu: Menu,
  plus: Plus,
  filter: ListFilter,
  'chevron-left': ChevronLeft,
  'chevron-right': ChevronRight,
  'chevron-up': ChevronUp,
  'chevron-down': ChevronDown,
  'arrow-up': ArrowUp,
  image: Image,
  map: Map,
  ellipsis: Ellipsis,
  upload: Upload,
  share: Share,
  location: MapPin,
  'location-plus': MapPinPlusInside,
  trash: Trash,
  edit: Pen,
  settings: Settings,
  logout: LogOut,
  notifications: Bell,
  home: Home,
  user: User,
  heart: Heart,
  sidebar: Sidebar,
  calendar: Calendar,
};

export default Icon;
export type { IconName };
