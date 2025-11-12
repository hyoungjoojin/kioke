import {
  ChevronDown,
  ChevronLeft,
  ChevronRight,
  ChevronUp,
  Ellipsis,
  Image,
  ListFilter,
  type LucideProps,
  Map,
  MapPin,
  MapPinPlusInside,
  Menu,
  Plus,
  Share,
  Trash,
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
  | 'image'
  | 'map'
  | 'ellipsis'
  | 'share'
  | 'location'
  | 'location-plus'
  | 'trash';

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
  image: Image,
  map: Map,
  ellipsis: Ellipsis,
  share: Share,
  location: MapPin,
  'location-plus': MapPinPlusInside,
  trash: Trash,
};

export default Icon;
export type { IconName };
