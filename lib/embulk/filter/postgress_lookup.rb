Embulk::JavaPlugin.register_filter(
  "postgress_lookup", "org.embulk.filter.postgress_lookup.PostgressLookupFilterPlugin",
  File.expand_path('../../../../classpath', __FILE__))
